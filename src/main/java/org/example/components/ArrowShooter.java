package org.example.components;

import org.example.jade.GameObject;
import org.example.jade.Prefabs;
import org.example.jade.Window;
import org.joml.Vector2f;

public class ArrowShooter extends Component {

    private StateMachine stateMachine;
    private final float shootingTime = 0.2f;
    private final float preparingTime = 0.8f;
    private boolean isShooting = false;
    private float animationTimer = 0.0f;

    @Override
    public void start() {
        this.stateMachine = gameObject.getComponent(StateMachine.class);
    }

    @Override
    public void update(float dt) {
        animationTimer += dt;

        if (!isShooting && animationTimer >= preparingTime) {
            this.stateMachine.trigger("shooting");
            isShooting = true;
            animationTimer = 0.0f;
        } else if (isShooting && animationTimer >= shootingTime) {
            this.stateMachine.trigger("preparing");
            isShooting = false;
            animationTimer = 0.0f;
            spawnArrow();
        }
    }

    private void spawnArrow() {
        Vector2f position = new Vector2f(this.gameObject.transform.position)
                .add(new Vector2f(-0.01f, -0.11f));
        GameObject arrow = Prefabs.generateDeadlyArrow(position);
        arrow.getComponent(Projectile.class).isArrow = true;
        Window.getScene().addGameObjectToScene(arrow);
    }
}
