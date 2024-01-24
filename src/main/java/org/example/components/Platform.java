package org.example.components;

import org.example.jade.GameObject;
import org.example.physics2d.Physics2D;
import org.example.physics2d.components.Box2DCollider;
import org.example.physics2d.components.Rigidbody2D;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

import java.util.concurrent.SynchronousQueue;

public class Platform extends Component {
    private transient Rigidbody2D rb;
    private transient PlayerController collidingPlayer = null;
    private transient boolean canFly = false;
    public transient boolean onGround = false;

    @Override
    public void start() {
        this.rb = gameObject.getComponent(Rigidbody2D.class);
        //AssetPool.getSound("assets/sounds/powerup_appears.ogg").play();
        rb.setNotSensor();
    }

    @Override
    public void update(float dt) {
        checkOnGround();
        if (onGround && collidingPlayer == null) {
            return;
        }

        if(canFly) {
            this.gameObject.transform.position.y += 0.003f;
        } else {
            this.gameObject.transform.position.y -= 0.003f;
        }

    }

    public void checkOnGround() {
        float innerPlayerWidth = 0.5f * 0.6f;
        float yVal = -0.05f;
        onGround = Physics2D.checkOnGround(this.gameObject, innerPlayerWidth, yVal);
    }

    @Override
    public void beginCollision(GameObject collidingObject, Contact contact, Vector2f contactNormal) {
        PlayerController playerController = collidingObject.getComponent(PlayerController.class);
        if (playerController != null) {
            collidingPlayer = playerController;
            canFly = true;
        }

        CuteEnemyAI cuteEnemy = collidingObject.getComponent(CuteEnemyAI.class);
        if (cuteEnemy != null) {
            cuteEnemy.stomp();
            contact.setEnabled(false);
            //AssetPool.getSound("assets/sounds/kick.ogg").play();
        }

        SlimeAI slime = collidingObject.getComponent(SlimeAI.class);
        if (slime != null) {
            slime.stomp(0);
            contact.setEnabled(false);
            //AssetPool.getSound("assets/sounds/kick.ogg").play();
        }
    }

    @Override
    public void endCollision(GameObject collidingObject, Contact contact, Vector2f contactNormal) {
        PlayerController playerController = collidingObject.getComponent(PlayerController.class);
        if (playerController != null) {
            collidingPlayer = null;
            canFly = false;
        }
    }
}
