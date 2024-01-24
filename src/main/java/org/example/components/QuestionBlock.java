package org.example.components;

import org.example.jade.GameObject;
import org.example.jade.Prefabs;
import org.example.jade.Window;

public class QuestionBlock extends Block {
    private enum BlockType {
        Diamond,
        Powerup,
        Invisibility,
        Enemy
    }

    public BlockType blockType = BlockType.Diamond;

    @Override
    void playerHit(PlayerController playerController) {
        switch(blockType) {
            case Diamond:
                doDiamond();
                break;
            case Powerup:
                doPowerup(playerController);
                break;
            case Invisibility:
                doInvisibility();
                break;
            case Enemy:
                spawnCuteEnemy();
                break;
        }

        StateMachine stateMachine = gameObject.getComponent(StateMachine.class);
        if (stateMachine != null) {
            stateMachine.trigger("setInactive");
            this.setInactive();
        }
    }

    private void doInvisibility() {
        spawnRing();
    }

    private void doPowerup(PlayerController playerController) {
        if(playerController.isInvisible()) {
            doDiamond();
            return;
        }

        if (playerController.isRegular()) {
            spawnBottle();
        } else {
            spawnNecklace();
        }
    }

    private void doDiamond() {
        GameObject coin = Prefabs.generateBlockDiamond();
        coin.transform.position.set(this.gameObject.transform.position);
        coin.transform.position.y += 0.25f;
        Window.getScene().addGameObjectToScene(coin);
    }

    private void spawnCuteEnemy() {
        GameObject enemy = Prefabs.generateCuteEnemy("assets/images/Owlet.png");
        enemy.transform.position.set(gameObject.transform.position);
        enemy.transform.position.y += 0.25f;
        Window.getScene().addGameObjectToScene(enemy);
    }

    private void spawnBottle() {
        GameObject bottle = Prefabs.generateBottle();
        bottle.transform.position.set(gameObject.transform.position);
        bottle.transform.position.y += 0.25f;
        Window.getScene().addGameObjectToScene(bottle);
    }

    private void spawnNecklace() {
        GameObject necklace = Prefabs.generateNecklace();
        necklace.transform.position.set(gameObject.transform.position);
        necklace.transform.position.y += 0.25f;
        Window.getScene().addGameObjectToScene(necklace);
    }

    private void spawnRing() {
        GameObject ring = Prefabs.generateRing();
        ring.transform.position.set(gameObject.transform.position);
        ring.transform.position.y += 0.25f;
        Window.getScene().addGameObjectToScene(ring);
    }
}
