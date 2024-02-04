package org.example.scenes;

import imgui.ImGui;
import imgui.ImVec2;
import org.example.components.*;
import org.example.jade.*;
import org.example.physics2d.components.Box2DCollider;
import org.example.physics2d.components.Rigidbody2D;
import org.example.physics2d.enums.BodyType;
import org.example.utils.AssetPool;
import org.joml.Vector2f;

import java.io.File;
import java.util.Collection;

public class LevelEditorSceneInitializer extends SceneInitializer {

    private Spritesheet solidSprites;
    private Spritesheet decorationSprites;
    private GameObject levelEditorStuff;

    public LevelEditorSceneInitializer() {

    }

    @Override
    public void init(Scene scene) {
        solidSprites = AssetPool.getSpritesheet("assets/images/spritesheets/blocks_spritesheet.png");
        decorationSprites = AssetPool.getSpritesheet("assets/images/spritesheets/decoration_spritesheet.png");
        Spritesheet gizmos = AssetPool.getSpritesheet("assets/images/gizmos.png");

        levelEditorStuff = scene.createGameObject("LevelEditor");
        levelEditorStuff.setNoSerialize();
        levelEditorStuff.addComponent(new MouseControls());
        levelEditorStuff.addComponent(new KeyControls());
        levelEditorStuff.addComponent(new GridLines());
        levelEditorStuff.addComponent(new EditorCamera(scene.camera()));
        levelEditorStuff.addComponent(new GizmoSystem(gizmos));
        scene.addGameObjectToScene(levelEditorStuff);
    }

    @Override
    public void loadResources(Scene scene) {
        // Shaders assets
        AssetPool.getShader("assets/shaders/default.glsl");

        // Sprites assets
        AssetPool.addSpritesheet("assets/images/spritesheets/blocks_spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/blocks_spritesheet.png"),
                        16, 16, 1780, 0));
        AssetPool.addSpritesheet("assets/images/spritesheets/decoration_spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/decoration_spritesheet.png"),
                        16, 16, 270, 0));


        AssetPool.addSpritesheet("assets/images/Owlet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/Owlet.png"),
                        32, 32, 105, 0));
        AssetPool.addSpritesheet("assets/images/Dude.png",
                new Spritesheet(AssetPool.getTexture("assets/images/Dude.png"),
                        32, 32, 105, 0));
        AssetPool.addSpritesheet("assets/images/Pink.png",
                new Spritesheet(AssetPool.getTexture("assets/images/Pink.png"),
                        32, 32, 105, 0));
        AssetPool.addSpritesheet("assets/images/breaker.png",
                new Spritesheet(AssetPool.getTexture("assets/images/breaker.png"),
                        51, 51, 72, 0));
        AssetPool.addSpritesheet("assets/images/slimeSheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/slimeSheet.png"),
                        32, 32, 21, 0));
        AssetPool.addSpritesheet("assets/images/lava.png",
                new Spritesheet(AssetPool.getTexture("assets/images/lava.png"),
                        32, 32, 4, 0));
        AssetPool.addSpritesheet("assets/images/water.png",
                new Spritesheet(AssetPool.getTexture("assets/images/water.png"),
                        16, 16, 32, 0));
        AssetPool.addSpritesheet("assets/images/spikes.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spikes.png"),
                        32, 16, 10, 0));
        AssetPool.addSpritesheet("assets/images/saw.png",
                new Spritesheet(AssetPool.getTexture("assets/images/saw.png"),
                        32, 32, 8, 0));
        AssetPool.addSpritesheet("assets/images/fan.png",
                new Spritesheet(AssetPool.getTexture("assets/images/fan.png"),
                        32, 16, 8, 0));
        AssetPool.addSpritesheet("assets/images/platform.png",
                new Spritesheet(AssetPool.getTexture("assets/images/platform.png"),
                        32, 16, 8, 0));
        AssetPool.addSpritesheet("assets/images/shooter_arrow.png",
                new Spritesheet(AssetPool.getTexture("assets/images/shooter_arrow.png"),
                        16, 16, 7, 0));
        AssetPool.addSpritesheet("assets/images/fire.png",
                new Spritesheet(AssetPool.getTexture("assets/images/fire.png"),
                        16, 16, 4, 0));
        AssetPool.addSpritesheet("assets/images/firebox.png",
                new Spritesheet(AssetPool.getTexture("assets/images/firebox.png"),
                        16, 16, 13, 0));


        AssetPool.addSpritesheet("assets/images/gizmos.png",
                new Spritesheet(AssetPool.getTexture("assets/images/gizmos.png"),
                        24, 48, 3, 0));
        AssetPool.addSpritesheet("assets/images/diamond.png",
                new Spritesheet(AssetPool.getTexture("assets/images/diamond.png"),
                        16, 16, 7, 0));
        AssetPool.addSpritesheet("assets/images/crate.png",
                new Spritesheet(AssetPool.getTexture("assets/images/crate.png"),
                        16, 16, 1, 0));
        AssetPool.addSpritesheet("assets/images/barrel.png",
                new Spritesheet(AssetPool.getTexture("assets/images/barrel.png"),
                        16, 16, 1, 0));
        AssetPool.addSpritesheet("assets/images/items.png",
                new Spritesheet(AssetPool.getTexture("assets/images/items.png"),
                        16, 16, 43, 0));
        AssetPool.addSpritesheet("assets/images/bottles.png",
                new Spritesheet(AssetPool.getTexture("assets/images/bottles.png"),
                        32, 32, 40, 0));
        AssetPool.addSpritesheet("assets/images/weapons.png",
                new Spritesheet(AssetPool.getTexture("assets/images/weapons.png"),
                        32, 32, 64, 0));
        AssetPool.addSpritesheet("assets/images/purplePortal.png",
                new Spritesheet(AssetPool.getTexture("assets/images/purplePortal.png"),
                        32, 48, 24, 0));
        AssetPool.addSpritesheet("assets/images/flag.png",
                new Spritesheet(AssetPool.getTexture("assets/images/flag.png"),
                        48, 64, 5, 0));
        AssetPool.addSpritesheet("assets/images/arrow.png",
                new Spritesheet(AssetPool.getTexture("assets/images/arrow.png"),
                        16, 16, 4, 0));
        AssetPool.addSpritesheet("assets/images/chest.png",
                new Spritesheet(AssetPool.getTexture("assets/images/chest.png"),
                        16, 16, 7, 0));


        AssetPool.addSpritesheet("assets/images/Hoodie.png",
                new Spritesheet(AssetPool.getTexture("assets/images/Hoodie.png"),
                        32, 32, 72, 0));
        AssetPool.addSpritesheet("assets/images/HoodieShadow.png",
                new Spritesheet(AssetPool.getTexture("assets/images/HoodieShadow.png"),
                        32, 32, 72, 0));
        AssetPool.addSpritesheet("assets/images/HoodieRed.png",
                new Spritesheet(AssetPool.getTexture("assets/images/HoodieRed.png"),
                        32, 32, 72, 0));
        AssetPool.addSpritesheet("assets/images/fireball.png",
                new Spritesheet(AssetPool.getTexture("assets/images/fireball.png"),
                        32, 32, 5, 0));


        // Sound assets
        AssetPool.addSound("assets/sounds/jump.ogg", false);
        AssetPool.addSound("assets/sounds/sus_musica.ogg", true);

        for (GameObject g : scene.getGameObjects()) {
            if (g.getComponent(SpriteRenderer.class) != null) {
                SpriteRenderer spr = g.getComponent(SpriteRenderer.class);
                if (spr.getTexture() != null) {
                    spr.setTexture(AssetPool.getTexture(spr.getTexture().getFilepath()));
                }
            }

            if (g.getComponent(StateMachine.class) != null) {
                StateMachine stateMachine = g.getComponent(StateMachine.class);
                stateMachine.refreshTextures();
            }
        }
    }

    @Override
    public void imgui() {
        ImGui.begin("Level Editor Stuff");
        levelEditorStuff.imgui();
        ImGui.end();

        ImGui.begin("LevelEditor Tools");

        if(ImGui.beginTabBar("WindowTabBar")) {
            if(ImGui.beginTabItem("Blocks")) {
                ImVec2 windowPos = new ImVec2();
                ImGui.getWindowPos(windowPos);
                ImVec2 windowSize = new ImVec2();
                ImGui.getWindowSize(windowSize);
                ImVec2 itemSpacing = new ImVec2();
                ImGui.getStyle().getItemSpacing(itemSpacing);

                float windowX2 = windowPos.x + windowSize.x;
                for (int i = 0; i < solidSprites.size(); i++) {
                    Sprite sprite = solidSprites.getSprite(i);
                    float spriteWidth = sprite.getWidth() * 4;
                    float spriteHeight = sprite.getHeight() * 4;
                    int id = sprite.getTexId();
                    Vector2f[] texCoords = sprite.getTexCoords();

                    ImGui.pushID(i);
                    if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                        GameObject object = Prefabs.generateSpriteObject(sprite, 0.25f, 0.25f);
                        Rigidbody2D rb = new Rigidbody2D();
                        rb.setBodyType(BodyType.Static);
                        object.addComponent(rb);
                        Box2DCollider b2d = new Box2DCollider();
                        b2d.setHalfSize(new Vector2f(0.25f, 0.25f));
                        object.addComponent(b2d);
                        object.addComponent(new Ground());
                        if (i == 20) {
                            object.addComponent(new BreakableObject());
                        }
                        levelEditorStuff.getComponent(MouseControls.class).pickupObject(object);
                    }
                    ImGui.popID();

                    ImVec2 lastButtonPos = new ImVec2();
                    ImGui.getItemRectMax(lastButtonPos);
                    float lastButtonX2 = lastButtonPos.x;
                    float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
                    if (i + 1 < solidSprites.size() && nextButtonX2 < windowX2) {
                        ImGui.sameLine();
                    }
                }

                ImGui.endTabItem();
            }

            if(ImGui.beginTabItem("Decorations")) {
                ImVec2 windowPos = new ImVec2();
                ImGui.getWindowPos(windowPos);
                ImVec2 windowSize = new ImVec2();
                ImGui.getWindowSize(windowSize);
                ImVec2 itemSpacing = new ImVec2();
                ImGui.getStyle().getItemSpacing(itemSpacing);

                float windowX2 = windowPos.x + windowSize.x;
                for (int i = 0; i < decorationSprites.size(); i++) {
                    Sprite sprite = decorationSprites.getSprite(i);
                    float spriteWidth = sprite.getWidth() * 4;
                    float spriteHeight = sprite.getHeight() * 4;
                    int id = sprite.getTexId();
                    Vector2f[] texCoords = sprite.getTexCoords();

                    ImGui.pushID(i);
                    if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                        GameObject object = Prefabs.generateSpriteObject(sprite, 0.25f, 0.25f);
                        levelEditorStuff.getComponent(MouseControls.class).pickupObject(object);
                    }
                    ImGui.popID();

                    ImVec2 lastButtonPos = new ImVec2();
                    ImGui.getItemRectMax(lastButtonPos);
                    float lastButtonX2 = lastButtonPos.x;
                    float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
                    if (i + 1 < decorationSprites.size() && nextButtonX2 < windowX2) {
                        ImGui.sameLine();
                    }
                }

                ImGui.endTabItem();
            }

            if(ImGui.beginTabItem("Special")){
                int uid = 0;
                float spriteWidth = 128.0f;
                float spriteHeight = 128.0f;

                handleSpriteButtonClick(spriteWidth, spriteHeight, uid,
                        "assets/images/Hoodie.png", Prefabs.generateMainCharacter(), false);
                /*Spritesheet playerSprites = AssetPool.getSpritesheet("assets/images/Hoodie.png");
                Sprite sprite = playerSprites.getSprite(0);
                float spriteWidth = sprite.getWidth() * 4;
                float spriteHeight = sprite.getHeight() * 4;
                int id = sprite.getTexId();
                Vector2f[] texCoords = sprite.getTexCoords();
                ImGui.pushID(uid++);
                if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                    GameObject object = Prefabs.generateMainCharacter();
                    levelEditorStuff.getComponent(MouseControls.class).pickupObject(object);
                }
                ImGui.popID();
                ImGui.sameLine();*/

                handleSpriteButtonClick(spriteWidth, spriteHeight, uid,
                        "assets/images/items.png", Prefabs.generateQuestionBlock(), false);
                handleSpriteButtonClick(spriteWidth, spriteHeight, uid,
                        "assets/images/crate.png", Prefabs.generateCrate(), false);
                handleSpriteButtonClick(spriteWidth, spriteHeight, uid,
                        "assets/images/barrel.png", Prefabs.generateBarrel(), false);
                handleSpriteButtonClick(spriteWidth, spriteHeight, uid,
                        "assets/images/lava.png", Prefabs.generateLava(), false);
                handleSpriteButtonClick(spriteWidth, spriteHeight, uid,
                        "assets/images/water.png", Prefabs.generateWater(), false);
                handleSpriteButtonClick(spriteWidth, spriteHeight, uid,
                        "assets/images/Owlet.png", Prefabs.generateCuteEnemy("assets/images/Owlet.png"), false);
                handleSpriteButtonClick(spriteWidth, spriteHeight, uid,
                        "assets/images/Pink.png", Prefabs.generateCuteEnemy("assets/images/Pink.png"), false);
                handleSpriteButtonClick(spriteWidth, spriteHeight, uid,
                        "assets/images/Dude.png", Prefabs.generateCuteEnemy("assets/images/Dude.png"), true);
                handleSpriteButtonClick(spriteWidth, spriteHeight, uid,
                        "assets/images/purplePortal.png", Prefabs.generatePortal(Direction.Left), false);
                handleSpriteButtonClick(spriteWidth, spriteHeight, uid,
                        "assets/images/breaker.png", Prefabs.generateBreaker(), false);
                handleSpriteButtonClick(spriteWidth, spriteHeight, uid,
                        "assets/images/slimeSheet.png", Prefabs.generateSlime(), false);
                handleSpriteButtonClick(spriteWidth, spriteHeight, uid,
                        "assets/images/flag.png", Prefabs.generateFlag(), false);
                handleSpriteButtonClick(spriteWidth, spriteHeight, uid,
                        "assets/images/spikes.png", Prefabs.generateSpikes(), false);
                handleSpriteButtonClick(spriteWidth, spriteHeight, uid,
                        "assets/images/saw.png", Prefabs.generateSaw(), false);
                handleSpriteButtonClick(spriteWidth, spriteHeight, uid,
                        "assets/images/fan.png", Prefabs.generateFan(), false);
                handleSpriteButtonClick(spriteWidth, spriteHeight, uid,
                        "assets/images/platform.png", Prefabs.generatePlatform(), false);
                handleSpriteButtonClick(spriteWidth, spriteHeight, uid,
                        "assets/images/arrow.png", Prefabs.generateJumpingArrow(), true);
                handleSpriteButtonClick(spriteWidth, spriteHeight, uid,
                        "assets/images/shooter_arrow.png", Prefabs.generateArrowShooter(), false);
                handleSpriteButtonClick(spriteWidth, spriteHeight, uid,
                        "assets/images/firebox.png", Prefabs.generateFirebox(), false);
                handleSpriteButtonClick(spriteWidth, spriteHeight, uid,
                        "assets/images/chest.png", Prefabs.generateChest(), false);

                ImGui.endTabItem();
            }

            if (ImGui.beginTabItem("Sounds")) {
                Collection<Sound> sounds = AssetPool.getAllSounds();
                for (Sound sound : sounds) {
                    File tmp = new File(sound.getFilepath());
                    if (ImGui.button(tmp.getName())) {
                        if (!sound.isPlaying()) {
                            sound.play();
                        } else {
                            sound.stop();
                        }
                    }

                    if (ImGui.getContentRegionAvailX() > 100) {
                        ImGui.sameLine();
                    }
                }

                ImGui.endTabItem();
            }
            ImGui.endTabBar();
        }

        ImGui.end();
    }

    private void handleSpriteButtonClick(float spriteWidth, float spriteHeight, int uid, String imagePath, GameObject gameObject, boolean newLine) {
        Spritesheet spritesheet = AssetPool.getSpritesheet(imagePath);
        Sprite sprite = spritesheet.getSprite(0);
        int id = sprite.getTexId();
        Vector2f[] texCoords = sprite.getTexCoords();

        ImGui.pushID(uid++);
        if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
            levelEditorStuff.getComponent(MouseControls.class).pickupObject(gameObject);
        }
        ImGui.popID();
        if(newLine){
            ImGui.newLine();
        } else {
            ImGui.sameLine();
        }
    }
}
