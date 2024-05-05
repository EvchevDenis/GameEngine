package org.example.components;

import org.example.jade.Camera;
import org.example.jade.GameObject;
import org.example.jade.Window;
import org.example.physics2d.Physics2D;
import org.example.physics2d.colliders.Rigidbody2D;
import org.example.utils.AssetPool;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

import java.util.Objects;

public class SlimeAI extends Component {
    private transient boolean goingRight = false;

    private transient boolean attacked = false;
    private transient Rigidbody2D rb;
    private transient float walkSpeed = 0.6f;
    private transient Vector2f velocity = new Vector2f();
    private transient Vector2f terminalVelocity = new Vector2f(2.1f, 3.1f);
    private transient Vector2f acceleration = new Vector2f();
    private transient boolean onGround = false;
    private final transient float enemyWidth = 0.25f;
    private transient boolean isDead = false;
    private transient boolean isMoving = false;
    private transient float timeToKill = 0.5f;
    private transient StateMachine stateMachine;

    private transient int slimeHP = 2;
    private transient float regenSlimeHPTime = 10.0f;

    @Override
    public void start() {
        this.stateMachine = gameObject.getComponent(StateMachine.class);
        this.rb = gameObject.getComponent(Rigidbody2D.class);
        this.acceleration.y = Window.getPhysics().getGravity().y;
    }

    @Override
    public void update(float dt) {
        Camera camera = Window.getScene().camera();
        if (this.gameObject.transform.position.x >
                camera.position.x + camera.getProjectionSize().x * camera.getZoom()) {
            return;
        }

        if (isDead) {
            timeToKill -= dt;
            if (timeToKill <= 0) {
                this.gameObject.destroy();
            }
            this.rb.setVelocity(new Vector2f());
            return;
        }

        if (goingRight && !attacked) {
            this.gameObject.transform.scale.x = -enemyWidth;
            velocity.x = walkSpeed;

            if (this.velocity.x < 0) {
                this.stateMachine.trigger("switchDirection");
            } else {
                this.stateMachine.trigger("startWalking");
            }
        } else if (!goingRight && !attacked){
            this.gameObject.transform.scale.x = enemyWidth;
            velocity.x = -walkSpeed;

            if (this.velocity.x > 0) {
                this.stateMachine.trigger("switchDirection");
            } else {
                this.stateMachine.trigger("startWalking");
            }
        } else if (goingRight) {
            this.gameObject.transform.scale.x = -enemyWidth;
            velocity.x = walkSpeed;
            regenSlimeHPTime -= dt;

            if (this.velocity.x < 0) {
                this.stateMachine.trigger("switchDirection");
            } else {
                this.stateMachine.trigger("startAttack");
            }
        } else {
            this.gameObject.transform.scale.x = enemyWidth;
            velocity.x = -walkSpeed;
            regenSlimeHPTime -= dt;

            if (this.velocity.x > 0) {
                this.stateMachine.trigger("switchDirection");
            } else {
                this.stateMachine.trigger("startAttack");
            }
        }

        if(regenSlimeHPTime <= 0) {
            setToDefaultState();
        }

        checkOnGround();
        if (onGround) {
            this.acceleration.y = 0;
            this.velocity.y = 0;
        } else {
            this.acceleration.y = Window.getPhysics().getGravity().y * 0.7f;
        }

        this.velocity.y += this.acceleration.y * dt;
        this.velocity.y = Math.max(Math.min(this.velocity.y, this.terminalVelocity.y), -terminalVelocity.y);
        this.rb.setVelocity(velocity);
    }

    public void setToDefaultState() {
        regenSlimeHPTime = 10.0f;
        attacked = false;
        slimeHP = 2;
        walkSpeed = 0.6f;
    }

    public void checkOnGround() {
        float innerPlayerWidth = 0.25f * 0.7f;
        float yVal = -0.7f;
        onGround = Physics2D.checkOnGround(this.gameObject, innerPlayerWidth, yVal);
    }

    @Override
    public void beginCollision(GameObject obj, Contact contact, Vector2f contactNormal) {
        if (isDead) {
            return;
        }

        PlayerController playerController = obj.getComponent(PlayerController.class);
        if (playerController != null) {
            if (!playerController.isDead() && !playerController.isHurtInvincible() &&
                    contactNormal.y > 0.58f) {
                playerController.enemyBounce();
                slimeHP -= 1;
                stomp(slimeHP);
            } else if (!playerController.isDead() && !playerController.isInvincible()) {
                playerController.die();
            }
        } else if (Math.abs(contactNormal.y) < 0.1f) {
            goingRight = contactNormal.x < 1;
        }

        if(obj.getComponent(Projectile.class) != null) {
            slimeHP -= 1;
            stomp(slimeHP);
            obj.getComponent(Projectile.class).disappear();
        }
    }

    public void stomp(int hp) {
        if (hp == 0) {
            this.isDead = true;
            this.isMoving = false;
            this.velocity.zero();
            this.rb.setVelocity(this.velocity);
            this.rb.setAngularVelocity(0.0f);
            this.rb.setGravityScale(0.0f);
            this.stateMachine.trigger("dead");
            Objects.requireNonNull(AssetPool.getSound("assets/sounds/bump.ogg")).play();
        }
        if (hp == 1){
            this.attacked = true;
            this.walkSpeed = 1.2f;
        }
    }
}
