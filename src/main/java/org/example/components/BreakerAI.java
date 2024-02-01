package org.example.components;

import org.example.jade.Camera;
import org.example.jade.GameObject;
import org.example.jade.Window;
import org.example.physics2d.Physics2D;
import org.example.physics2d.components.Rigidbody2D;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

import java.security.SecureRandom;

public class BreakerAI extends Component {

    private transient GameObject player;
    private transient Rigidbody2D rb;
    private transient float flySpeed = 0.6f;
    private transient Vector2f velocity = new Vector2f();
    private transient Vector2f acceleration = new Vector2f();
    private transient Vector2f terminalVelocity = new Vector2f();
    private final transient float enemyWidth = 0.50f;
    private transient StateMachine stateMachine;

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
        if (Math.abs(player.transform.position.x - this.gameObject.transform.position.x) <= 5.0f) {
            if (Math.abs(player.transform.position.x - this.gameObject.transform.position.x) <= 0.25f) {
                stopFlying();
            } else {
                if (player.transform.position.x - this.gameObject.transform.position.x > 0) {
                    moveRight();
                } else {
                    moveLeft();
                }
            }
        } else {
            stopFlying();
        }

        this.velocity.y += this.acceleration.y * dt;
        this.velocity.y = Math.max(Math.min(this.velocity.y, this.terminalVelocity.y), -terminalVelocity.y);
        this.rb.setVelocity(velocity);
    }

    public void stopFlying() {
        this.gameObject.transform.scale.x = enemyWidth;
        velocity.x = 0;
    }

    public void moveRight() {
        this.gameObject.transform.scale.x = enemyWidth;
        velocity.x = flySpeed;

        if (this.velocity.x < 0) {
            this.stateMachine.trigger("switchDirection");
        } else {
            this.stateMachine.trigger("startWalking");
        }
    }

    public void moveLeft() {
        this.gameObject.transform.scale.x = -enemyWidth;
        velocity.x = -flySpeed;

        if (this.velocity.x > 0) {
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
            //AssetPool.getSound("assets/sounds/kick.ogg").play();
        }

        SlimeAI slime = obj.getComponent(SlimeAI.class);
        if (slime != null) {
            slime.stomp(0);
            contact.setEnabled(false);
            //AssetPool.getSound("assets/sounds/kick.ogg").play();
        }
    }

}
