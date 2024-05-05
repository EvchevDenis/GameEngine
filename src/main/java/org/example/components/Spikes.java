package org.example.components;

import org.example.jade.GameObject;
import org.example.physics2d.colliders.Rigidbody2D;
import org.example.utils.AssetPool;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

import java.util.Objects;

public class Spikes extends Component {
    private transient Rigidbody2D rb;

    @Override
    public void start() {
        this.rb = gameObject.getComponent(Rigidbody2D.class);
        this.rb.setIsSensor();
    }

    @Override
    public void beginCollision(GameObject obj, Contact contact, Vector2f contactNormal) {
        PlayerController playerController = obj.getComponent(PlayerController.class);
        if (playerController != null) {
            playerController.die();
            contact.setEnabled(false);
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

