package org.example.components;

import org.example.jade.GameObject;
import org.example.physics2d.colliders.Rigidbody2D;
import org.example.utils.AssetPool;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

import java.util.Objects;

public class Water extends Component {
    private transient Rigidbody2D rb;

    @Override
    public void start() {
        this.rb = gameObject.getComponent(Rigidbody2D.class);
        this.rb.setIsSensor();
    }

    @Override
    public void beginCollision(GameObject obj, Contact contact, Vector2f contactNormal) {
        PlayerController playerController = obj.getComponent(PlayerController.class);
        if (playerController != null) {
            playerController.walkSpeed = 0.3f;
            playerController.slowDownForce = 0.2f;
            playerController.terminalVelocity = new Vector2f(1.1f, 1.1f);
        }
    }

    @Override
    public void endCollision(GameObject collidingObject, Contact contact, Vector2f contactNormal) {
        PlayerController playerController = collidingObject.getComponent(PlayerController.class);
        if (playerController != null) {
            if(playerController.isRegular()) {
                playerController.walkSpeed = 1.1f;
                playerController.slowDownForce = 0.05f;
                playerController.terminalVelocity = new Vector2f(2.1f, 3.1f);
            } else if (playerController.isShadow()) {
                playerController.walkSpeed *= 1.05f + 3;
                playerController.slowDownForce = 0.05f;
                playerController.terminalVelocity = new Vector2f(2.1f, 3.1f);
            } else if (playerController.isRed()) {
                playerController.walkSpeed *= 1.05f + 5;
                playerController.slowDownForce = 0.05f;
                playerController.terminalVelocity = new Vector2f(2.1f, 3.1f);
            }
        }
    }
}
