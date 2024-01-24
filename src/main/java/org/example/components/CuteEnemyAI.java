package org.example.components;

import org.example.jade.Camera;
import org.example.jade.GameObject;
import org.example.jade.Window;
import org.example.physics2d.Physics2D;
import org.example.physics2d.components.Rigidbody2D;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;


public class CuteEnemyAI extends Component {

    private transient GameObject player;
    private transient boolean goingRight = false;
    private transient Rigidbody2D rb;
    private transient float walkSpeed = 0.6f;
    private transient Vector2f velocity = new Vector2f();
    private transient Vector2f acceleration = new Vector2f();
    private transient Vector2f terminalVelocity = new Vector2f();
    private transient boolean onGround = false;
    private final transient float enemyWidth = 0.25f;
    private transient boolean isDead = false;
    private transient float timeToKill = 0.5f;
    private transient StateMachine stateMachine;

    private transient float lastXPosition;
    private transient float stuckTimer;
    private transient float stuckCooldown;

    private transient boolean isKiller = false;

    @Override
    public void start() {
        this.player = Window.getScene().getGameObjectWith(PlayerController.class);
        this.stateMachine = gameObject.getComponent(StateMachine.class);
        this.rb = gameObject.getComponent(Rigidbody2D.class);
        this.acceleration.y = Window.getPhysics().getGravity().y;
    }

    @Override
    public void update(float dt) {
        if (isDead) {
            timeToKill -= dt;
            if (timeToKill <= 0) {
                this.gameObject.destroy();
            }
            this.rb.setVelocity(new Vector2f());
            return;
        }

        float currentXPosition = this.gameObject.transform.position.x;

        if (Math.abs(currentXPosition - lastXPosition) < 0.001f) {
            stuckTimer += dt;
            if (stuckTimer > 0.1f) {
                if (!isStuckCooldownActive()) {
                    if (goingRight) {
                        moveLeft();
                    } else {
                        moveRight();
                    }
                    stuckTimer = 0.0f;
                    startStuckCooldown();
                }
            }
        } else {
            stuckTimer = 0.0f;
        }

        lastXPosition = currentXPosition;

        if (isStuckCooldownActive()) {
            if (goingRight) {
                moveRight();
            } else {
                moveLeft();
            }
            updateStuckCooldown(dt);
        } else {
            if(Math.abs(player.transform.position.y - this.gameObject.transform.position.y) <= 0.16f){
                if(player.transform.position.x > this.gameObject.transform.position.x) {
                    moveRight();
                } else if(player.transform.position.x < this.gameObject.transform.position.x) {
                    moveLeft();
                }
            }
        }

        checkOnGround();
        if (onGround) {
            this.acceleration.y = 0;
            this.velocity.y = 0;
        } else {
            this.velocity.y *= 0.35f;
            this.acceleration.y = Window.getPhysics().getGravity().y * 0.7f;
        }

        this.velocity.y += this.acceleration.y * dt;
        this.velocity.y = Math.max(Math.min(this.velocity.y, this.terminalVelocity.y), -terminalVelocity.y);
        this.rb.setVelocity(velocity);
    }

    private boolean isStuckCooldownActive() {
        return stuckCooldown > 0;
    }

    private void startStuckCooldown() {
        stuckCooldown = 2.0f;
    }

    private void updateStuckCooldown(float dt) {
        if (isStuckCooldownActive()) {
            stuckCooldown -= dt;
            if (stuckCooldown <= 0) {
                stuckCooldown = 0;
            }
        }
    }

    public void moveRight() {
        this.gameObject.transform.scale.x = enemyWidth;
        velocity.x = walkSpeed;

        if (this.velocity.x < 0) {
            this.stateMachine.trigger("stopWalking");
        } else {
            this.stateMachine.trigger("startWalking");
        }
    }

    public void moveLeft() {
        this.gameObject.transform.scale.x = -enemyWidth;
        velocity.x = -walkSpeed;

        if (this.velocity.x > 0) {
            this.stateMachine.trigger("stopWalking");
        } else {
            this.stateMachine.trigger("startWalking");
        }
    }

    public void checkOnGround() {
        float innerPlayerWidth = 0.25f * 0.7f;
        float yVal = -0.14f;
        onGround = Physics2D.checkOnGround(this.gameObject, innerPlayerWidth, yVal);
    }

    @Override
    public void preSolve(GameObject obj, Contact contact, Vector2f contactNormal) {
        if (isDead) {
            return;
        }

        PlayerController playerController = obj.getComponent(PlayerController.class);
        if (playerController != null) {
            if (playerController.isInvisible()) {
                contact.setEnabled(false);
                isKiller = false;
                return;
            } else {
                isKiller = true;
            }

            if (!playerController.isDead() && !playerController.isHurtInvincible() &&
                    contactNormal.y > 0.58f) {
                playerController.enemyBounce();
                stomp();
            } else if (!playerController.isDead() && !playerController.isInvincible() && isKiller) {
                playerController.die();
            }
        }

        if (Math.abs(contactNormal.x) > 0.8f) {
            goingRight = contactNormal.x < 0;
        }

        if(obj.getComponent(Projectile.class) != null) {
            stomp();
            obj.getComponent(Projectile.class).disappear();
        }
    }

    public void stomp() {
        this.isDead = true;
        this.velocity.zero();
        this.rb.setVelocity(new Vector2f());
        this.rb.setAngularVelocity(0.0f);
        this.rb.setGravityScale(0.0f);
        this.stateMachine.trigger("dead");
        this.rb.setIsSensor();
    }
}
