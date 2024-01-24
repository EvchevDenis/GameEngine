package org.example.components;

import org.example.jade.GameObject;
import org.example.jade.Prefabs;
import org.example.jade.Window;
import org.joml.Vector2f;

public class ArrowShooter extends Component {
    private float animationTime = 0.0f;
    private boolean isShooting = false;

    @Override
    public void start() {

    }

    @Override
    public void update(float dt) {
        animationTime += dt;

        if (isShooting) {
            if (animationTime >= 0.2f) {
                isShooting = false;
                animationTime = 0.0f;
            }
        } else {
            if (animationTime >= 0.8f) {
                isShooting = true;
                animationTime = 0.0f;
                spawnArrow();
            }
        }
    }

    private void spawnArrow() {
        Vector2f position = new Vector2f(this.gameObject.transform.position)
                .add(new Vector2f(-0.01f, -0.11f));
        //this.stateMachine.trigger("attack");
        GameObject arrow = Prefabs.generateDeadlyArrow(position);
        arrow.getComponent(Projectile.class).isArrow = true;
        Window.getScene().addGameObjectToScene(arrow);
    }
}
