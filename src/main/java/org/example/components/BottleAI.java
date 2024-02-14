package org.example.components;

import org.example.jade.GameObject;
import org.example.physics2d.colliders.Rigidbody2D;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

public class BottleAI extends Component {
    private transient boolean goingRight = true;
    private transient Rigidbody2D rb;
    private transient Vector2f speed = new Vector2f(1.0f, 0.0f);
    private transient float maxSpeed = 0.3f;
    private transient boolean hitPlayer = false;

    @Override
    public void start() {
        this.rb = gameObject.getComponent(Rigidbody2D.class);
        //AssetPool.getSound("assets/sounds/powerup_appears.ogg").play();
    }

    @Override
    public void update(float dt) {
        if (goingRight && Math.abs(rb.getVelocity().x) < maxSpeed) {
            rb.addVelocity(speed);
        } else if (!goingRight && Math.abs(rb.getVelocity().x) < maxSpeed) {
            rb.addVelocity(new Vector2f(-speed.x, speed.y));
        }
    }

    @Override
    public void preSolve(GameObject obj, Contact contact, Vector2f contactNormal) {
        PlayerController playerController = obj.getComponent(PlayerController.class);
        if (playerController != null) {
            contact.setEnabled(false);
            if (!hitPlayer) {
                playerController.powerup();
                this.gameObject.destroy();
                hitPlayer = true;
            }
        }

        if (Math.abs(contactNormal.y) < 0.1f) {
            goingRight = contactNormal.x < 0;
        }
    }
}
