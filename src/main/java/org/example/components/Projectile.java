package org.example.components;

import org.example.jade.GameObject;
import org.example.jade.Window;
import org.example.physics2d.Physics2D;
import org.example.physics2d.components.Rigidbody2D;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

public class Projectile extends Component {

    public transient boolean goingRight;
    private transient Rigidbody2D rb;
    private transient float projectileSpeed = 7.7f;
    private transient Vector2f velocity = new Vector2f();
    private transient Vector2f acceleration = new Vector2f();
    private transient Vector2f terminalVelocity = new Vector2f(2.1f, 3.1f);
    private transient boolean onGround = false;

    private static int projectileCount = 0;

    private final transient float projectileWidth = 0.1f;

    public transient boolean isFireball = false;
    public transient boolean isArrow = false;

    public static boolean canSpawn() {
        return projectileCount < 4;
    }

    @Override
    public void start() {
        this.rb = this.gameObject.getComponent(Rigidbody2D.class);
        this.acceleration.y = Window.getPhysics().getGravity().y * 0.7f;
        this.rb.setIsSensor();
        projectileCount++;
    }

    @Override
    public void update(float dt) {
        //lifetime -= dt;
        if (onGround) {
            disappear();
            return;
        }

        if(isFireball) {
            if (goingRight) {
                this.gameObject.transform.scale.x = projectileWidth;
                velocity.x = projectileSpeed;
            } else {
                this.gameObject.transform.scale.x = -projectileWidth;
                velocity.x = -projectileSpeed;
            }
        }

        if(isArrow) {
            velocity.y = -projectileSpeed;
        }

        checkOnGround();
        if (onGround) {
            this.acceleration.y = 1.5f;
            this.velocity.y = 2.5f;
        } else {
            this.acceleration.y = Window.getPhysics().getGravity().y * 0.7f;
        }

        this.velocity.y += this.acceleration.y * dt;
        this.velocity.y = Math.max(Math.min(this.velocity.y, this.terminalVelocity.y), -terminalVelocity.y);
        this.rb.setVelocity(velocity);
    }

    public void checkOnGround() {
        float innerPlayerWidth = 0.25f * 0.7f;
        float yVal = -0.09f;
        onGround = Physics2D.checkOnGround(this.gameObject, innerPlayerWidth, yVal);
    }

    @Override
    public void beginCollision(GameObject obj, Contact contact, Vector2f contactNormal) {
        PlayerController playerController = obj.getComponent(PlayerController.class);
        if(playerController != null && isArrow) {
            playerController.die();
        }

        if (Math.abs(contactNormal.x) > 0.8f) {
            this.goingRight = contactNormal.x < 0;
        }
    }

    @Override
    public void preSolve(GameObject obj, Contact contact, Vector2f contactNormal) {
        if (obj.getComponent(PlayerController.class) != null &&
                obj.getComponent(Projectile.class) != null) {
            contact.setEnabled(false);
        }
    }

    public void disappear() {
        projectileCount--;
        this.gameObject.destroy();
    }
}
