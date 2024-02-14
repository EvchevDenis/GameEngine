package org.example.components;

import org.example.jade.GameObject;
import org.example.jade.Window;
import org.example.physics2d.Physics2D;
import org.example.physics2d.colliders.Rigidbody2D;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

public class SurikenNf extends Component {
    public transient boolean goingRight = false;
    private transient Rigidbody2D rb;
    private transient float surikenSpeed = 1.7f;
    private transient Vector2f velocity = new Vector2f();
    private transient Vector2f acceleration = new Vector2f();
    private transient Vector2f terminalVelocity = new Vector2f(2.1f, 3.1f);
    private transient boolean onGround = false;
    private transient int reboundQuantity = 10;

    private final transient float surikenWidth = 0.5f;

    @Override
    public void start() {
        this.rb = this.gameObject.getComponent(Rigidbody2D.class);
        this.acceleration.y = Window.getPhysics().getGravity().y * 0.7f;
    }

    @Override
    public void update(float dt) {
        if (reboundQuantity == 0) {
            disappear();
            return;
        }

        if (goingRight) {
            this.gameObject.transform.scale.x = surikenWidth;
            velocity.x = surikenSpeed;
        } else {
            this.gameObject.transform.scale.x = -surikenWidth;
            velocity.x = -surikenSpeed;
        }

        checkOnGround();
        if (onGround) {
            this.acceleration.y = 10.5f;
            //this.velocity.y = 2.5f;
            reboundQuantity--;
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
        if (Math.abs(contactNormal.x) > 0.8f) {
            this.goingRight = contactNormal.x < 0;
        }
    }

    @Override
    public void preSolve(GameObject obj, Contact contact, Vector2f contactNormal) {
        PlayerController playerController  = obj.getComponent(PlayerController.class);
        if (playerController != null) {
            playerController.die();
        }
    }

    public void disappear() {
        this.gameObject.destroy();
    }
}
