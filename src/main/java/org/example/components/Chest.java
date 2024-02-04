package org.example.components;

import org.example.jade.GameObject;
import org.example.jade.Prefabs;
import org.example.jade.Window;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

public class Chest extends Component {
    private enum BlockType {
        Diamond,
        Powerup,
        Invisibility,
        Enemy
    }

    private transient boolean opened = false;

    private Chest.BlockType blockType = Chest.BlockType.Diamond;

    @Override
    public void beginCollision(GameObject obj, Contact contact, Vector2f contactNormal) {
        PlayerController playerController = obj.getComponent(PlayerController.class);
        StateMachine stateMachine = gameObject.getComponent(StateMachine.class);
        if (playerController != null && !opened) {
            switch (blockType) {
                case Diamond:
                    doDiamond();
                    break;
                case Powerup:
                    doPowerup(playerController);
                    break;
                case Invisibility:
                    spawnRing();
                    break;
                case Enemy:
                    spawnCuteEnemy();
                    break;
            }
            stateMachine.trigger("opening");
            opened = true;
        }

        if (stateMachine != null) {
            stateMachine.trigger("setInactive");
        }
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

