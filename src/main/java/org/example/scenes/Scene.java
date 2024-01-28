package org.example.scenes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.components.Component;
import org.example.components.ComponentDeserializer;
import org.example.jade.*;
import org.example.physics2d.Physics2D;
import org.example.renderer.Renderer;
import org.joml.Vector2f;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class Scene {

    private Renderer renderer;
    private Camera camera;
    private boolean isRunning;
    private List<GameObject> gameObjects;
    private List<GameObject> pendingObjects;
    private Physics2D physics2D;

    private SceneInitializer sceneInitializer;
    private String currentLevel;

    public Scene(SceneInitializer sceneInitializer) {
        this.sceneInitializer = sceneInitializer;
        this.physics2D = new Physics2D();
        this.renderer = new Renderer();
        this.gameObjects = new ArrayList<>();
        this.pendingObjects = new ArrayList<>();
        this.isRunning = false;
    }

    public Physics2D getPhysics() {
        return this.physics2D;
    }

    public void init() {
        this.camera = new Camera(new Vector2f(0, 0));
        this.sceneInitializer.loadResources(this);
        this.sceneInitializer.init(this);
    }

    public void start() {
        for (int i=0; i < gameObjects.size(); i++) {
            GameObject go = gameObjects.get(i);
            go.start();
            this.renderer.add(go);
            this.physics2D.add(go);
        }
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject go) {
        if (!isRunning) {
            gameObjects.add(go);
        } else {
            pendingObjects.add(go);
        }
    }

    public void destroy() {
        for (GameObject go : gameObjects) {
            go.destroy();
        }
    }

    public <T extends Component> GameObject getGameObjectWith(Class<T> clazz) {
        for (GameObject go : gameObjects) {
            if (go.getComponent(clazz) != null) {
                return go;
            }
        }

        return null;
    }

    public List<GameObject> getGameObjects() {
        return this.gameObjects;
    }

    public GameObject getGameObject(int gameObjectId) {
        Optional<GameObject> result = this.gameObjects.stream()
                .filter(gameObject -> gameObject.getUid() == gameObjectId)
                .findFirst();
        return result.orElse(null);
    }

    public GameObject getGameObject(String gameObjectName) {
        Optional<GameObject> result = this.gameObjects.stream()
                .filter(gameObject -> gameObject.name.equals(gameObjectName))
                .findFirst();
        return result.orElse(null);
    }

    public void editorUpdate(float dt) {
        this.camera.adjustProjection();

        for (int i=0; i < gameObjects.size(); i++) {
            GameObject go = gameObjects.get(i);
            go.editorUpdate(dt);

            if (go.isDead()) {
                gameObjects.remove(i);
                this.renderer.destroyGameObject(go);
                this.physics2D.destroyGameObject(go);
                i--;
            }
        }

        for (GameObject go : pendingObjects) {
            gameObjects.add(go);
            go.start();
            this.renderer.add(go);
            this.physics2D.add(go);
        }
        pendingObjects.clear();
    }

    public void update(float dt) {
        this.camera.adjustProjection();
        this.physics2D.update(dt);

        for (int i=0; i < gameObjects.size(); i++) {
            GameObject go = gameObjects.get(i);
            go.update(dt);

            if (go.isDead()) {
                gameObjects.remove(i);
                this.renderer.destroyGameObject(go);
                this.physics2D.destroyGameObject(go);
                i--;
            }
        }

        for (GameObject go : pendingObjects) {
            gameObjects.add(go);
            go.start();
            this.renderer.add(go);
            this.physics2D.add(go);
        }
        pendingObjects.clear();
    }

    public void render() {
        this.renderer.render();
    }

    public Camera camera() {
        return this.camera;
    }

    public void imgui() {
        this.sceneInitializer.imgui();
    }

    public GameObject createGameObject(String name) {
        GameObject go = new GameObject(name);
        go.addComponent(new Transform());
        go.transform = go.getComponent(Transform.class);
        return go;
    }

    public void save() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .enableComplexMapKeySerialization()
                .create();

        try {
            FileWriter writer = new FileWriter(currentLevel);
            List<GameObject> objsToSerialize = new ArrayList<>();
            for (GameObject obj : this.gameObjects) {
                if (obj.doSerialization()) {
                    objsToSerialize.add(obj);
                }
            }
            writer.write(gson.toJson(objsToSerialize));
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAs() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .enableComplexMapKeySerialization()
                .create();

        JFileChooser saveChooser = windowsJFileChooser(new JFileChooser());
        saveChooser.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter("TXT file", "txt");
        saveChooser.setFileFilter(filter);
        saveChooser.addChoosableFileFilter(filter);
        saveChooser.setDialogTitle("Save File");
        saveChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        saveChooser.setAcceptAllFileFilterUsed(false);

        if (saveChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                FileWriter writer = new FileWriter(saveChooser.getSelectedFile());
                List<GameObject> objsToSerialize = new ArrayList<>();
                for (GameObject obj : this.gameObjects) {
                    if (obj.doSerialization()) {
                        objsToSerialize.add(obj);
                    }
                }
                writer.write(gson.toJson(objsToSerialize));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void load() {
        Properties properties = new Properties();
        String loadPath = null;
        try (FileInputStream input = new FileInputStream("config.cfg")) {
            properties.load(input);
            loadPath = properties.getProperty("PreviousLevel");
            currentLevel = loadPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadDataFromFile(loadPath);
    }

    public void loadFrom() {
        Properties properties = new Properties();
        String loadPath = null;
        boolean fileChooserClosed = false;
        JFileChooser loadChooser = windowsJFileChooser(new JFileChooser());
        loadChooser.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter("TXT file", "txt");
        loadChooser.setFileFilter(filter);
        loadChooser.addChoosableFileFilter(filter);
        loadChooser.setDialogTitle("Load File");
        loadChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        loadChooser.setAcceptAllFileFilterUsed(false);

        if (loadChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = loadChooser.getSelectedFile();
            loadPath = file.getAbsolutePath();
            System.out.println("Path to file : " + loadPath);
        } else {
            try (FileInputStream input = new FileInputStream("config.cfg")) {
                properties.load(input);
                loadPath = properties.getProperty("PreviousLevel");
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileChooserClosed = true;
        }

        if(!fileChooserClosed) {
            properties.setProperty("PreviousLevel", loadPath);
            try (FileOutputStream output = new FileOutputStream("config.cfg")) {
                properties.store(output, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        loadDataFromFile(loadPath);
    }

    public void loadDataFromFile(String loadPath) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .enableComplexMapKeySerialization()
                .create();

        String inFile = "";
        try {
            inFile = new String(Files.readAllBytes(Paths.get(loadPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!inFile.isEmpty()) {
            int maxGoId = -1;
            int maxCompId = -1;
            GameObject[] objs = gson.fromJson(inFile, GameObject[].class);
            for (int i=0; i < objs.length; i++) {
                addGameObjectToScene(objs[i]);

                for (Component c : objs[i].getAllComponents()) {
                    if (c.getUid() > maxCompId) {
                        maxCompId = c.getUid();
                    }
                }
                if (objs[i].getUid() > maxGoId) {
                    maxGoId = objs[i].getUid();
                }
            }

            maxGoId++;
            maxCompId++;
            GameObject.init(maxGoId);
            Component.init(maxCompId);
        }

        currentLevel = loadPath;
    }

    public static JFileChooser windowsJFileChooser(JFileChooser chooser){
        LookAndFeel previousLF = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            chooser = new JFileChooser();
            UIManager.setLookAndFeel(previousLF);
        } catch (IllegalAccessException | UnsupportedLookAndFeelException | InstantiationException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return chooser;
    }
}

