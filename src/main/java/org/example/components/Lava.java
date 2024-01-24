package org.example.components;

import org.example.jade.GameObject;
import org.example.physics2d.components.Rigidbody2D;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

public class Lava extends Component {
    private transient Rigidbody2D rb;

    @Override
    public void start() {
        this.rb = gameObject.getComponent(Rigidbody2D.class);
        //AssetPool.getSound("assets/sounds/powerup_appears.ogg").play();
        this.rb.setIsSensor();
    }

    @Override
    public void beginCollision(GameObject obj, Contact contact, Vector2f contactNormal) {
        PlayerController playerController = obj.getComponent(PlayerController.class);
        if (playerController != null) {
            playerController.die();
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
