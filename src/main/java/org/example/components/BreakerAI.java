package org.example.components;

import org.example.jade.GameObject;
import org.example.jade.Window;
import org.example.physics2d.colliders.Rigidbody2D;
import org.example.utils.AssetPool;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

import java.util.Objects;

public class BreakerAI extends Component {

    private transient GameObject player;
    private transient Rigidbody2D rb;
    private transient float flySpeed = 0.6f;
    private transient Vector2f velocity = new Vector2f();
    private transient Vector2f acceleration = new Vector2f();
    private transient Vector2f terminalVelocity = new Vector2f(0.5f, 0.5f);
    private transient StateMachine stateMachine;
    private transient boolean shouldMoveUpDown = false;

    @Override
    public void start() {
        this.player = Window.getScene().getGameObjectWith(PlayerController.class);
        this.stateMachine = gameObject.getComponent(StateMachine.class);
        this.rb = gameObject.getComponent(Rigidbody2D.class);
        this.acceleration.y = Window.getPhysics().getGravity().y;
        this.rb.setIsSensor();
    }

    @Override
    public void update(float dt) {
        float distanceX = player.transform.position.x - this.gameObject.transform.position.x;
        float distanceY = player.transform.position.y - this.gameObject.transform.position.y;

        if (Math.abs(distanceX) <= 5.0f) {
            if (Math.abs(distanceX) <= 0.25f) {
                stopFlying();
            } else {
                if (Math.abs(distanceY) <= 0.1f) {
                    shouldMoveUpDown = true;
                } else {
                    shouldMoveUpDown = false;

                    if (distanceX > 0) {
                        moveRight();
                    } else if (distanceX < 0) {
                        moveLeft();
                    }

                    if (distanceY > 0) {
                        moveUp();
                    } else if (distanceY < 0) {
                        moveDown();
                    }
                }
            }
        } else {
            this.gameObject.destroy();
        }

        if (shouldMoveUpDown) {
            this.acceleration.y = Math.signum(distanceY);
        } else {
            this.acceleration.y = 0.0f;
        }

        this.velocity.x += this.acceleration.x * dt;
        this.velocity.y += this.acceleration.y * dt;
        this.velocity.x = Math.max(Math.min(this.velocity.x, this.terminalVelocity.x), -this.terminalVelocity.x);
        this.velocity.y = Math.max(Math.min(this.velocity.y, this.terminalVelocity.y), -this.terminalVelocity.y);
        this.rb.setVelocity(velocity);
    }

    public void stopFlying() {
        velocity.x = 0;
    }

    public void moveRight() {
        velocity.x = flySpeed;

        if (this.velocity.x < 0) {
            this.stateMachine.trigger("switchDirection");
        } else {
            this.stateMachine.trigger("startWalking");
        }
    }

    public void moveLeft() {
        velocity.x = -flySpeed;

        if (this.velocity.x > 0) {
            this.stateMachine.trigger("switchDirection");
        } else {
            this.stateMachine.trigger("startWalking");
        }
    }

    public void moveDown() {
        velocity.y = -flySpeed;

        if (this.velocity.y > 0) {
            this.stateMachine.trigger("switchDirection");
        } else {
            this.stateMachine.trigger("startWalking");
        }
    }

    public void moveUp() {
        velocity.y = flySpeed;

        if (this.velocity.y > 0) {
            this.stateMachine.trigger("switchDirection");
        } else {
            this.stateMachine.trigger("startWalking");
        }
    }

    @Override
    public void beginCollision(GameObject obj, Contact contact, Vector2f contactNormal) {
        PlayerController playerController = obj.getComponent(PlayerController.class);

        if (playerController != null) {
            if (!playerController.isDead() && !playerController.isInvincible()) {
                playerController.die();
            }
        }

        CuteEnemyAI cuteEnemy = obj.getComponent(CuteEnemyAI.class);
        if (cuteEnemy != null) {
            cuteEnemy.stomp();
            contact.setEnabled(false);
        }

        SlimeAI slime = obj.getComponent(SlimeAI.class);
        if (slime != null) {
            slime.stomp(0);
            contact.setEnabled(false);
        }
    }

}
