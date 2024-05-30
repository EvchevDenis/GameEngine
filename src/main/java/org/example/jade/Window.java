package org.example.jade;

import org.example.components.SpriteRenderer;
import org.example.observers.EventSystem;
import org.example.observers.Observer;
import org.example.observers.events.Event;
import org.example.physics2d.Physics2D;
import org.example.renderer.*;
import org.example.renderer.Renderer;
import org.example.scenes.LevelEditorSceneInitializer;
import org.example.scenes.LevelSceneInitializer;
import org.example.scenes.Scene;
import org.example.scenes.SceneInitializer;
import org.example.utils.AssetPool;
import org.example.utils.ImageParser;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.opengl.GL;

import java.util.List;
import java.util.Objects;

import static org.example.utils.Settings.SCREEN_HEIGHT;
import static org.example.utils.Settings.SCREEN_WIDTH;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window implements Observer {

    public static final boolean GAME_RELEASE = false;

    private int width, height;
    private String title;
    private long glfwWindow;
    private ImGuiLayer imguiLayer;
    private Framebuffer framebuffer;
    private PickingTexture pickingTexture;
    private boolean runtimePlaying = false;

    private static Window window = null;

    private long audioContext;
    private long audioDevice;

    private static Scene currentScene;

    private final ImageParser iconImage = ImageParser.loadImage("assets/icon.png");

    private Window() {
        this.width = SCREEN_WIDTH;
        this.height = SCREEN_HEIGHT;
        this.title = "Game Engine";
        EventSystem.addObserver(this);
    }

    public static void changeScene(SceneInitializer sceneInitializer, boolean useLevelChooser) {
        if (currentScene != null) {
            currentScene.destroy();
        }

        if (!GAME_RELEASE) {
            getImguiLayer().getPropertiesWindow().setActiveGameObject(null);
        }

        currentScene = new Scene(sceneInitializer);
        if(!useLevelChooser) {
            currentScene.loadLevel();
        } else {
            currentScene.loadLevelFrom();
        }
        currentScene.init();
        currentScene.start();
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    public static Physics2D getPhysics() { return currentScene.getPhysics(); }

    public static Scene getScene() {
        return currentScene;
    }

    public void run() {
        init();
        loop();

        // Destroy the audio context
        alcDestroyContext(audioContext);
        alcCloseDevice(audioDevice);

        // Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and the free the error callback
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    public void init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        // Installation of all peripheral and window callbacks
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
        glfwSetWindowSizeCallback(glfwWindow, (w, newWidth, newHeight) -> {
            Window.setWidth(newWidth);
            Window.setHeight(newHeight);
        });

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // Initialize the audio device
        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        audioDevice = alcOpenDevice(defaultDeviceName);

        int[] attributes = {0};
        float maxVolume = 0.3f;
        audioContext = alcCreateContext(audioDevice, attributes);
        alcMakeContextCurrent(audioContext);

        ALCCapabilities alcCapabilities = ALC.createCapabilities(audioDevice);
        ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);

        if (!alCapabilities.OpenAL10) {
            assert false : "Audio library not supported.";
        }
        alListenerf(AL_GAIN, maxVolume);

        // Critical for LWJGL's interoperation with GLFW's
        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ONE);

        this.framebuffer = new Framebuffer(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.pickingTexture = new PickingTexture(SCREEN_WIDTH, SCREEN_HEIGHT);
        glViewport(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        if (GAME_RELEASE) {
            runtimePlaying = true;
            Window.changeScene(new LevelSceneInitializer(), false);
        } else {
            this.imguiLayer = new ImGuiLayer(glfwWindow, pickingTexture);
            this.imguiLayer.initImGui();
            Window.changeScene(new LevelEditorSceneInitializer(), false);
        }

        GLFWImage image = GLFWImage.malloc();
        GLFWImage.Buffer imageBuffer = GLFWImage.malloc(1);
        image.set(iconImage.getWidth(), iconImage.getHeight(), iconImage.getImage());
        imageBuffer.put(0, image);
        glfwSetWindowIcon(glfwWindow, imageBuffer);
    }

    public void loop() {
        float beginTime = (float)glfwGetTime();
        float endTime;
        float dt = -1.0f;
        int frames = 0;
        String fpsText;
        long timer = System.currentTimeMillis();

        Shader defaultShader = AssetPool.getShader("assets/shaders/default.glsl");
        Shader pickingShader = AssetPool.getShader("assets/shaders/pickingShader.glsl");

        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events
            glfwPollEvents();

            // Render pass 1. Render to picking texture
            glDisable(GL_BLEND);
            pickingTexture.enableWriting();

            glViewport(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

            glClearColor(0, 0, 0, 0);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            Renderer.bindShader(pickingShader);
            currentScene.render();

            pickingTexture.disableWriting();
            glEnable(GL_BLEND);

            // Render pass 2. Render actual game
            DebugDraw.beginFrame();

            this.framebuffer.bind();
            Vector4f clearColor = currentScene.camera().clearColor;
            glClearColor(clearColor.x, clearColor.y, clearColor.z, clearColor.w);
            glClear(GL_COLOR_BUFFER_BIT);

            if (dt >= 0) {
                Renderer.bindShader(defaultShader);
                if (runtimePlaying) {
                    currentScene.update(dt);
                } else {
                    currentScene.editorUpdate(dt);
                }
                currentScene.render();
                DebugDraw.draw();
            }
            this.framebuffer.unbind();

            if (GAME_RELEASE) {
                glBindFramebuffer(GL_READ_FRAMEBUFFER, framebuffer.getFboID());
                glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
                glBlitFramebuffer(0, 0, framebuffer.width, framebuffer.height, 0, 0, this.width, this.height,
                        GL_COLOR_BUFFER_BIT, GL_NEAREST);
            } else {
                this.imguiLayer.update(dt, currentScene, runtimePlaying);
            }

            KeyListener.endFrame();
            MouseListener.endFrame();
            glfwSwapBuffers(glfwWindow);

            endTime = (float)glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;

            // FPS calculation
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                fpsText = "FPS: " + frames;
                //System.out.println(fpsText);
                frames = 0;
            }
        }
    }

    public static int getWindowWidth() {
        return SCREEN_WIDTH;
    }

    public static int getWindowHeight() {
        return SCREEN_HEIGHT;
    }

    public static void setWidth(int newWidth) {
        get().width = newWidth;
    }

    public static void setHeight(int newHeight) {
        get().height = newHeight;
    }

    public static Framebuffer getFramebuffer() {
        return get().framebuffer;
    }

    public static float getTargetAspectRatio() {
        return 16.0f / 9.0f;
    }

    public static ImGuiLayer getImguiLayer() {
        return get().imguiLayer;
    }

    @Override
    public void onNotify(GameObject object, Event event) {
        switch (event.type) {
            case GameEngineStartPlay:
                List<GameObject> gameObjects = getScene().getGameObjects();
                for (GameObject go : gameObjects) {
                    SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
                    if(spr == null || spr.getColor().equals(new Vector4f(1, 1, 1, 1))) {
                        continue;
                    }
                    spr.setColor(new Vector4f(1, 1, 1, 1));
                }

                this.runtimePlaying = true;
                currentScene.saveLevel();
                Window.changeScene(new LevelSceneInitializer(), false);
                break;
            case GameEngineStopPlay:
                this.runtimePlaying = false;
                Window.changeScene(new LevelEditorSceneInitializer(), false);
                break;
            case LoadLevel:
            case ImportedAssetFile:
            case DeleteImportedAsset:
                Window.changeScene(new LevelEditorSceneInitializer(), false);
                break;
            case LoadLevelFrom:
                Window.changeScene(new LevelEditorSceneInitializer(), true);
                break;
            case SaveLevel:
                currentScene.saveLevel();
                break;
            case SaveLevelAs:
                currentScene.saveAsLevel();
                break;
            case CreateNewLevel:
                currentScene.createNewLevel();
                Window.changeScene(new LevelEditorSceneInitializer(), false);
                break;
            case EncryptCurrentLevel:
                currentScene.encryptCurrentLevel();
                break;
            case EncryptLevel:
                currentScene.encryptLevel();
                break;
            case DecryptLevel:
                currentScene.decryptLevel();
                break;
        }
    }
}