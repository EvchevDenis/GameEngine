package org.example.components;

import org.example.jade.GameObject;
import org.example.physics2d.colliders.Box2DCollider;
import org.example.physics2d.colliders.Rigidbody2D;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

public class Fan extends Component {
    private transient Rigidbody2D rb;
    private transient Box2DCollider box2DCollider;
    private transient PlayerController collidingPlayer = null;

    @Override
    public void start() {
        this.rb = gameObject.getComponent(Rigidbody2D.class);
        this.box2DCollider = this.gameObject.getComponent(Box2DCollider.class);
        //AssetPool.getSound("assets/sounds/powerup_appears.ogg").play();
        rb.setIsSensor();
    }

    @Override
    public void update(float dt) {
        if(collidingPlayer == null){
            return;
        }

        Vector2f playerPosition = this.collidingPlayer.gameObject.transform.position;

        float collider1LeftX = this.gameObject.transform.position.x - this.box2DCollider.getHalfSize().x;
        float collider1RightX = this.gameObject.transform.position.x + this.box2DCollider.getHalfSize().x;
        float collider1TopY = this.gameObject.transform.position.y + this.box2DCollider.getHalfSize().y;
        float collider1BottomY = this.gameObject.transform.position.y - this.box2DCollider.getHalfSize().y;

        if (playerPosition.x >= collider1LeftX && playerPosition.x <= collider1RightX
                && playerPosition.y >= collider1BottomY && playerPosition.y <= collider1TopY) {
            this.collidingPlayer.flyingOnFan = true;
            this.collidingPlayer.setPlayerGravity(-6.0f);
            this.collidingPlayer.gameObject.transform.position.y += dt;
        } else {
            this.collidingPlayer.flyingOnFan = false;
            this.collidingPlayer.setPlayerGravity(0.0f);
            this.collidingPlayer = null;
        }
    }

    @Override
    public void beginCollision(GameObject collidingObject, Contact contact, Vector2f contactNormal) {
        PlayerController playerController = collidingObject.getComponent(PlayerController.class);
        if (playerController != null && collidingPlayer == null) {
            collidingPlayer = playerController;
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
}
