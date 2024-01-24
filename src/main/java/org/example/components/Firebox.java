package org.example.components;

import org.example.jade.GameObject;
import org.example.jade.Prefabs;
import org.example.jade.Window;
import org.example.physics2d.components.Rigidbody2D;
import org.joml.Vector2f;

public class Firebox extends Component {
    private transient StateMachine stateMachine;
    private float animationTime = 0.0f;
    private boolean isFiring = false;

    @Override
    public void start() {
        this.stateMachine = gameObject.getComponent(StateMachine.class);
    }

    @Override
    public void update(float dt) {
        animationTime += dt;

        if (isFiring) {
            if (animationTime >= 0.4f) {
                animationTime = 0.0f;
                this.isFiring = false;
            }
        } else {
            if (animationTime >= 1.3f) {
                animationTime = 0.0f;
                this.isFiring = true;
                if (Fire.canSpawn()) {
                    Vector2f position = new Vector2f(this.gameObject.transform.position)
                            .add(new Vector2f(0, 0.25f));
                    GameObject fire = Prefabs.generateFire(position);
                    Window.getScene().addGameObjectToScene(fire);
                    fire.getComponent(Fire.class).startFiring();
                }
            }
        }
    }
}
