package org.example.components;

import org.example.jade.GameObject;
import org.example.physics2d.colliders.Rigidbody2D;
import org.example.utils.AssetPool;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

import java.util.Objects;

public class Necklace extends Component {
    private transient Rigidbody2D rb;

    @Override
    public void start() {
        this.rb = gameObject.getComponent(Rigidbody2D.class);
        Objects.requireNonNull(AssetPool.getSound("assets/sounds/powerup_appears.ogg")).play();
        this.rb.setIsSensor();
    }

    @Override
    public void beginCollision(GameObject obj, Contact contact, Vector2f contactNormal) {
        PlayerController playerController = obj.getComponent(PlayerController.class);
        if (playerController != null) {
            playerController.powerup();
            this.gameObject.destroy();
        }
    }
}
