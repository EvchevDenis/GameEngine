package org.example.scenes;

import org.example.components.*;
import org.example.jade.GameObject;
import org.example.utils.AssetPool;

public class LevelSceneInitializer extends SceneInitializer {
    public LevelSceneInitializer() {

    }

    @Override
    public void init(Scene scene) {
        GameObject cameraObject = scene.createGameObject("GameCamera");
        cameraObject.addComponent(new GameCamera(scene.camera()));
        cameraObject.start();
        scene.addGameObjectToScene(cameraObject);
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


        // Sound assets - can't loop
        AssetPool.addSound("assets/sounds/jump.ogg", false);
        AssetPool.addSound("assets/sounds/powerup.ogg", false);
        AssetPool.addSound("assets/sounds/you_win.ogg", false);
        AssetPool.addSound("assets/sounds/die.ogg", false);
        AssetPool.addSound("assets/sounds/hurt.ogg", false);
        AssetPool.addSound("assets/sounds/powerup_appears.ogg", false);
        AssetPool.addSound("assets/sounds/enemy_death.ogg", false);
        AssetPool.addSound("assets/sounds/bump.ogg", false);
        AssetPool.addSound("assets/sounds/coin.ogg", false);
        AssetPool.addSound("assets/sounds/portal.ogg", false);

        // Sound assets - can loop
        AssetPool.addSound("assets/sounds/theme.ogg", true);

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

    }
}
