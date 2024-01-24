package org.example.components;

import org.example.jade.GameObject;
import org.example.physics2d.components.Rigidbody2D;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

public class JumpingArrow extends Component {
    private transient Rigidbody2D rb;
    private transient PlayerController collidingPlayer = null;
    private float wait = 0.5f;

    @Override
    public void start() {
        this.rb = gameObject.getComponent(Rigidbody2D.class);
        rb.setIsSensor();
    }

    @Override
    public void update(float dt) {
        if(collidingPlayer != null) {
            if(wait <= 0) {
                collidingPlayer.flyingOnArrow = false;
            }
            wait -= dt;
            if(!collidingPlayer.flyingOnArrow) {
                wait = 0.5f;
                collidingPlayer = null;
            }
        }
    }

    @Override
    public void beginCollision(GameObject collidingObject, Contact contact, Vector2f contactNormal) {
        PlayerController playerController = collidingObject.getComponent(PlayerController.class);
        if (playerController != null) {
            collidingPlayer = playerController;
            collidingPlayer.flyingOnArrow = true;
        }
    }
}
