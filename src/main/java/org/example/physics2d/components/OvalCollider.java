package org.example.physics2d.components;

import org.example.components.Component;
import org.example.jade.Window;
import org.joml.Vector2f;

public class OvalCollider extends Component {
    private transient CircleCollider bottomCircle = new CircleCollider();
    private transient CircleCollider centerCircle = new CircleCollider();
    private transient CircleCollider upperCircle = new CircleCollider();
    private transient boolean resetFixtureNextFrame = false;

    public float width = 0.1f;
    public Vector2f offset = new Vector2f();

    @Override
    public void start() {
        this.bottomCircle.gameObject = this.gameObject;
        this.centerCircle.gameObject = this.gameObject;
        this.upperCircle.gameObject = this.gameObject;
        recalculateColliders();
    }

    @Override
    public void editorUpdate(float dt) {
        bottomCircle.editorUpdate(dt);
        centerCircle.editorUpdate(dt);
        upperCircle.editorUpdate(dt);
        recalculateColliders();

        if (resetFixtureNextFrame) {
            resetFixture();
        }
    }

    @Override
    public void update(float dt) {
        if (resetFixtureNextFrame) {
            resetFixture();
        }
    }

    public void setWidth(float newVal) {
        this.width = newVal;
        recalculateColliders();
        resetFixture();
    }

    public void resetFixture() {
        if (Window.getPhysics().isLocked()) {
            resetFixtureNextFrame = true;
            return;
        }
        resetFixtureNextFrame = false;

        if (gameObject != null) {
            Rigidbody2D rb = gameObject.getComponent(Rigidbody2D.class);
            if (rb != null) {
                Window.getPhysics().resetOvalCollider(rb, this);
            }
        }
    }

    public void recalculateColliders() {
        float circleRadius = width;

        bottomCircle.setRadius(circleRadius);
        bottomCircle.setOffset(new Vector2f(offset).add(0, -0.06f));

        centerCircle.setRadius(circleRadius);
        centerCircle.setOffset(new Vector2f(offset).add(0, -0.01f));

        upperCircle.setRadius(circleRadius);
        upperCircle.setOffset(new Vector2f(offset).add(0, 0.03f));

    }

    public CircleCollider getBottomCircle() {
        return bottomCircle;
    }

    public CircleCollider getCenterCircle() {
        return centerCircle;
    }

    public CircleCollider getUpperCircle() {
        return upperCircle;
    }
}
