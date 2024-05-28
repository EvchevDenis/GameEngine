package org.example.components;

import org.example.jade.GameObject;
import org.example.physics2d.colliders.Rigidbody2D;
import org.example.utils.AssetPool;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

import java.util.Objects;

public class Bottle extends Component {
    @Override
    public void start() {
        Objects.requireNonNull(AssetPool.getSound("assets/sounds/powerup_appears.ogg")).play();
    }

    @Override
    public void preSolve(GameObject obj, Contact contact, Vector2f contactNormal) {
        PlayerController playerController = obj.getComponent(PlayerController.class);
        if (playerController != null) {
            contact.setEnabled(false);
            playerController.powerup();
            this.gameObject.destroy();
        }
    }
}
