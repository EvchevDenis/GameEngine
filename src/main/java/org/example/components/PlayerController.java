package org.example.components;

import org.example.jade.*;
import org.example.observers.EventSystem;
import org.example.observers.events.Event;
import org.example.observers.events.EventType;
import org.example.physics2d.Physics2D;
import org.example.physics2d.components.PillboxCollider;
import org.example.physics2d.components.Rigidbody2D;
import org.example.physics2d.enums.BodyType;
import org.example.scenes.LevelEditorSceneInitializer;
import org.example.scenes.LevelSceneInitializer;
import org.example.utils.AssetPool;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerController extends Component {

    private enum PlayerState {
        Regular,
        Shadow,
        Red,
        Invincible,
        Invisible
    }
    public float walkSpeed = 1.1f;
    public float jumpBoost = 1.0f;
    public float jumpImpulse = 3.0f;
    public float slowDownForce = 0.05f;
    public Vector2f terminalVelocity = new Vector2f(2.1f, 3.1f);
    private PlayerState playerState = PlayerState.Regular;
    public transient boolean onGround = false;
    private transient float groundDebounce = 0.0f;
    private final transient float groundDebounceTime = 0.1f;
    private transient Rigidbody2D rb;
    private transient StateMachine stateMachine;
    private final transient float bigJumpBoostFactor = 1.05f;
    private final transient float playerWidth = 0.25f;
    private transient int jumpTime = 0;
    private final transient Vector2f acceleration = new Vector2f();
    private final transient Vector2f velocity = new Vector2f();
    private transient boolean isDead = false;
    private transient int enemyBounce = 0;

    private transient float hurtInvincibilityTimeLeft = 0;
    private final transient float hurtInvincibilityTime = 1.4f;
    private transient float dyingTime = 1.5f;
    private transient float blinkTime = 0.0f;
    private transient SpriteRenderer spr;

    private transient boolean playWinAnimation = false;
    private transient float timeToCastle = 4.5f;
    private transient float walkTime = 2.2f;

    private transient float invisibilityTime = 5.0f;

    public transient boolean flyingOnFan = false;
    public transient boolean flyingOnArrow = false;

    public void defaultCharacteristics() {
        this.walkSpeed = 1.1f;
        this.jumpBoost = 1.0f;
        this.jumpImpulse = 3.0f;
        this.slowDownForce = 0.05f;
    }

    @Override
    public void start() {
        this.spr = gameObject.getComponent(SpriteRenderer.class);
        this.rb = gameObject.getComponent(Rigidbody2D.class);
        this.stateMachine = gameObject.getComponent(StateMachine.class);
        this.rb.setGravityScale(0.0f);
    }

    public void setPlayerGravity(float gravity) {
        this.rb.setGravityScale(gravity);
    }

    @Override
    public void update(float dt) {
        if (isDead) {
            if(dyingTime > 0) {
                dyingTime -= dt;
            } else {
                this.rb.setBodyType(BodyType.Kinematic);
                Window.changeScene(new LevelSceneInitializer(), false);
            }
            return;
        }

        if (playWinAnimation) {
            checkOnGround();
            if (!onGround) {
                gameObject.transform.scale.x = -0.25f;
                gameObject.transform.position.y -= dt;
                stateMachine.trigger("stopRunning");
                stateMachine.trigger("stopJumping");
            } else {
                if (this.walkTime > 0) {
                    gameObject.transform.scale.x = 0.25f;
                    gameObject.transform.position.x += dt;
                    stateMachine.trigger("startRunning");
                } else if (this.walkTime <= 0) {
                    walkSpeed = 0;
                    stateMachine.trigger("stopRunning");
                }
                /*if (!AssetPool.getSound("assets/sounds/stage_clear.ogg").isPlaying()) {
                    AssetPool.getSound("assets/sounds/stage_clear.ogg").play();
                }*/
                timeToCastle -= dt;
                walkTime -= dt;

                if (timeToCastle <= 0) {
                    Window.changeScene(new LevelEditorSceneInitializer(), false);
                    EventSystem.notify(null, new Event(EventType.GameEngineStopPlay));
                }
            }
            return;
        }

        if (hurtInvincibilityTimeLeft > 0) {
            hurtInvincibilityTimeLeft -= dt;
            blinkTime -= dt;

            if (blinkTime <= 0) {
                blinkTime = 0.1f;
                if (spr.getColor().w == 1) {
                    spr.setColor(new Vector4f(1, 1, 1, 0));
                } else {
                    spr.setColor(new Vector4f(1, 1, 1, 1));
                }
            } else {
                if (spr.getColor().w == 0) {
                    spr.setColor(new Vector4f(1, 1, 1, 1));
                }
            }
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT) || KeyListener.isKeyPressed(GLFW_KEY_D)) {
            this.gameObject.transform.scale.x = playerWidth;
            this.acceleration.x = walkSpeed;

            if (this.velocity.x < 0 && !isInvisible()) {
                this.stateMachine.trigger("switchDirection");
                this.velocity.x += slowDownForce;
            }

            if (this.velocity.x > 0 && !isInvisible()){
                this.stateMachine.trigger("startRunning");
            }
        } else if (KeyListener.isKeyPressed(GLFW_KEY_LEFT) || KeyListener.isKeyPressed(GLFW_KEY_A)) {
            this.gameObject.transform.scale.x = -playerWidth;
            this.acceleration.x = -walkSpeed;

            if (this.velocity.x > 0 && !isInvisible()) {
                this.stateMachine.trigger("switchDirection");
                this.velocity.x -= slowDownForce;
            }
            if (this.velocity.x < 0 && !isInvisible()) {
                this.stateMachine.trigger("startRunning");
            }
        } else {
            this.acceleration.x = 0;
            if (this.velocity.x > 0) {
                this.velocity.x = Math.max(0, this.velocity.x - slowDownForce);
            } else if (this.velocity.x < 0) {
                this.velocity.x = Math.min(0, this.velocity.x + slowDownForce);
            }

            if (this.velocity.x == 0 && !isInvisible()) {
                this.stateMachine.trigger("stopRunning");
            }
        }

        if (KeyListener.keyBeginPress(GLFW_KEY_E) && playerState == PlayerState.Red &&
                Projectile.canSpawn()) {
            Vector2f position = new Vector2f(this.gameObject.transform.position)
                    .add(this.gameObject.transform.scale.x > 0
                            ? new Vector2f(0.1f, 0)
                            : new Vector2f(-0.1f, 0));
            //this.stateMachine.trigger("attack");
            GameObject fireball = Prefabs.generateFireball(position);
            fireball.getComponent(Projectile.class).isFireball = true;
            fireball.getComponent(Projectile.class).goingRight =
                    this.gameObject.transform.scale.x > 0;
            Window.getScene().addGameObjectToScene(fireball);
        }

        checkOnGround();
        if(flyingOnArrow) {
            jumpUsage(dt, 50, false);
        } else if (!flyingOnFan) {
            jumpUsage(dt, 28, true);
        } else {
            if (!onGround) {
                this.velocity.y *= 0.35f;
                groundDebounce -= dt;
                this.acceleration.y = Window.getPhysics().getGravity().y * 0.7f;
            } else {
                this.velocity.y = 0;
                this.acceleration.y = 0;
                groundDebounce = groundDebounceTime;
            }
        }

        this.velocity.x += this.acceleration.x * dt;
        this.velocity.y += this.acceleration.y * dt;
        this.velocity.x = Math.max(Math.min(this.velocity.x, this.terminalVelocity.x), -this.terminalVelocity.x);
        this.velocity.y = Math.max(Math.min(this.velocity.y, this.terminalVelocity.y), -this.terminalVelocity.y);
        this.rb.setVelocity(this.velocity);
        this.rb.setAngularVelocity(0);

        if (!onGround && !isInvisible()) {
            stateMachine.trigger("jump");
        } else {
            stateMachine.trigger("stopJumping");
        }

        if(isInvisible() && invisibilityTime > 0) {
            invisibilityTime -= dt;
        } else {
            setRegular();
            invisibilityTime = 5.0f;
        }
    }

    public void jumpUsage(float dt, int jumpingTime, boolean jumpWithSpace) {
        if (jumpWithSpace ? (KeyListener.isKeyPressed(GLFW_KEY_SPACE) && (jumpTime > 0 || onGround || groundDebounce > 0)) : ((jumpTime > 0 || onGround || groundDebounce > 0))) {
            if ((onGround || groundDebounce > 0) && jumpTime == 0) {
                AssetPool.getSound("assets/sounds/jump.ogg").play();
                jumpTime = jumpingTime;
                this.velocity.y = jumpImpulse;
            } else if (jumpTime > 0) {
                jumpTime--;
                this.velocity.y = ((jumpTime / 2.2f) * jumpBoost);
            } else {
                this.velocity.y = 0;
            }
            groundDebounce = 0;
        } else if (enemyBounce > 0) {
            enemyBounce--;
            this.velocity.y = ((enemyBounce / 2.2f) * jumpBoost);
        } else if (!onGround) {
            if (this.jumpTime > 0) {
                this.velocity.y = 0.35f;
                this.jumpTime = 0;
            }
            groundDebounce -= dt;
            this.acceleration.y = Window.getPhysics().getGravity().y * 0.7f;
        } else {
            this.velocity.y = 0;
            this.acceleration.y = 0;
            groundDebounce = groundDebounceTime;
        }
    }

    public void checkOnGround() {
        float innerPlayerWidth = this.playerWidth * 0.6f;
        float yVal = -0.14f;
        onGround = Physics2D.checkOnGround(this.gameObject, innerPlayerWidth, yVal);
    }

    public void setPosition(Vector2f newPosition) {
        this.gameObject.transform.position.set(newPosition);
        this.rb.setPosition(newPosition);
    }

    public void powerup() {
        stateMachine.trigger("powerup");
        if (playerState == PlayerState.Regular) {
            playerState = PlayerState.Shadow;
            //AssetPool.getSound("assets/sounds/powerup.ogg").play();
            PillboxCollider pb = gameObject.getComponent(PillboxCollider.class);
            if (pb != null) {
                jumpBoost *= bigJumpBoostFactor + 3;
                walkSpeed *= bigJumpBoostFactor + 3;
            }
        } else if (playerState == PlayerState.Shadow) {
            playerState = PlayerState.Red;
            //AssetPool.getSound("assets/sounds/powerup.ogg").play();
            PillboxCollider pb = gameObject.getComponent(PillboxCollider.class);
            if (pb != null) {
                jumpBoost *= bigJumpBoostFactor + 5;
                walkSpeed *= bigJumpBoostFactor + 5;
            }
        }
    }

    @Override
    public void beginCollision(GameObject collidingObject, Contact contact, Vector2f contactNormal) {
        if (isDead) return;

        if (collidingObject.getComponent(Ground.class) != null) {
            if (Math.abs(contactNormal.x) > 0.8f) {
                this.velocity.x = 0;
            } else if (contactNormal.y > 0.8f) {
                this.velocity.y = 0;
                this.acceleration.y = 0;
                this.jumpTime = 0;
            }
        }
    }

    public void playWinAnimation(GameObject flagpole) {
        if (!playWinAnimation) {
            playWinAnimation = true;
            velocity.set(0.0f, 0.0f);
            acceleration.set(0.0f, 0.0f);
            rb.setVelocity(velocity);
            rb.setIsSensor();
            rb.setBodyType(BodyType.Static);
            //AssetPool.getSound("assets/sounds/flagpole.ogg").play();
        }
    }

    public void enemyBounce() {
        this.enemyBounce = 15;
    }

    public boolean isDead() {
        return this.isDead;
    }

    public boolean isHurtInvincible() {
        return this.hurtInvincibilityTimeLeft > 0 || playWinAnimation;
    }

    public boolean isInvincible() {
        return this.playerState == PlayerState.Invincible ||
                this.hurtInvincibilityTimeLeft > 0 || playWinAnimation;
    }

    public void die() {
        if (this.playerState == PlayerState.Regular) {
            this.rb.setBodyType(BodyType.Dynamic);
            this.velocity.set(0, 0);
            this.acceleration.set(0, 0);
            setPlayerGravity(3.0f);
            this.isDead = true;
            //this.rb.setIsSensor();
            //AssetPool.getSound("assets/sounds/die.ogg").play();
        } else if (this.playerState == PlayerState.Shadow) {
            this.playerState = PlayerState.Regular;
            PillboxCollider pb = gameObject.getComponent(PillboxCollider.class);
            if (pb != null) {
                defaultCharacteristics();
            }
            hurtInvincibilityTimeLeft = hurtInvincibilityTime;
            //AssetPool.getSound("assets/sounds/pipe.ogg").play();
        } else if (playerState == PlayerState.Red) {
            this.playerState = PlayerState.Shadow;
            PillboxCollider pb = gameObject.getComponent(PillboxCollider.class);
            if (pb != null) {
                jumpBoost *= bigJumpBoostFactor + 3;
                walkSpeed *= bigJumpBoostFactor + 3;
            }
            hurtInvincibilityTimeLeft = hurtInvincibilityTime;
            //AssetPool.getSound("assets/sounds/pipe.ogg").play();
        }
        this.stateMachine.trigger("die");
    }

    public void setInvisible() {
        if (this.playerState == PlayerState.Regular
                || this.playerState == PlayerState.Shadow
                || this.playerState == PlayerState.Red) {
            this.playerState = PlayerState.Invisible;
            jumpBoost *= bigJumpBoostFactor + 10;
            walkSpeed *= bigJumpBoostFactor + 10;
            this.stateMachine.trigger("invisible");
        }
    }

    public void setRegular() {
        if (this.playerState == PlayerState.Invisible) {
            this.playerState = PlayerState.Regular;
            PillboxCollider pb = gameObject.getComponent(PillboxCollider.class);
            if (pb != null) {
                defaultCharacteristics();
            }
            this.stateMachine.trigger("notInvisible");
        }
    }

    public boolean isRegular() {
        return this.playerState == PlayerState.Regular;
    }

    public boolean isShadow() {
        return this.playerState == PlayerState.Shadow;
    }

    public boolean isRed() {
        return this.playerState == PlayerState.Red;
    }

    public boolean isInvisible() {
        return this.playerState == PlayerState.Invisible;
    }

    public boolean hasWon() {
        return false;
    }
}
