package org.example.components;

import org.example.jade.GameObject;
import org.example.jade.Window;
import org.example.physics2d.Physics2D;
import org.example.physics2d.components.Rigidbody2D;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

public class Fire extends Component {

    private static int fireCount = 0;
    private transient float firingTime = 0;
    private transient Rigidbody2D rb;
    private boolean isFiring = false;

    private transient boolean isGround;

    public static boolean canSpawn() {
        return fireCount < 1;
    }

    @Override
    public void start() {
        this.rb = this.gameObject.getComponent(Rigidbody2D.class);
        this.rb.setIsSensor();
        fireCount++;
    }

    @Override
    public void update(float dt) {
        if (isFiring) {
            firingTime += dt;
            checkGround();
            gameObject.transform.position.y += dt / 2;
            gameObject.transform.scale.y -= dt / 10;
            gameObject.transform.scale.x -= dt / 10;
            if (isGround || firingTime > 1.5f) {
                disappear();
            }
        }
    }

    public void checkGround() {
        float innerPlayerWidth = 0.25f * 0.7f;
        float yVal = -0.14f;
        isGround = Physics2D.checkOnCeiling(this.gameObject, innerPlayerWidth, yVal);
    }

    @Override
    public void beginCollision(GameObject obj, Contact contact, Vector2f contactNormal) {
        PlayerController playerController = obj.getComponent(PlayerController.class);
        if (playerController != null) {
            playerController.die();
            fireCount--;
        }
    }

    public void startFiring() {
        isFiring = true;
    }

    public void disappear() {
        isFiring = false;
        fireCount--;
        this.gameObject.destroy();
    }
}
