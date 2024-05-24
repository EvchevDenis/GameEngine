package org.example.physics2d.colliders;

import org.example.components.Component;
import org.example.jade.Window;
import org.joml.Vector2f;

public class PillboxCollider extends Component {
    private transient CircleCollider bottomCircle = new CircleCollider();
    private transient Box2DCollider box2DCollider = new Box2DCollider();
    private transient boolean resetFixtureNextFrame = false;

    public float width = 0.1f;
    public float height = 0.2f;
    public Vector2f offset = new Vector2f();

    @Override
    public void start() {
        this.bottomCircle.gameObject = this.gameObject;
        this.box2DCollider.gameObject = this.gameObject;
        recalculateColliders();
    }

    @Override
    public void editorUpdate(float dt) {
        bottomCircle.editorUpdate(dt, this.gameObject);
        box2DCollider.editorUpdate(dt, this.gameObject);
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

    public void setHeight(float newVal) {
        this.height = newVal;
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
                Window.getPhysics().resetPillboxCollider(rb, this);
            }
        }
    }

    public void recalculateColliders() {
        float circleRadius = width / 2.0f;
        float boxHeight = height - circleRadius;
        bottomCircle.setRadius(circleRadius);
        bottomCircle.setOffset(new Vector2f(offset).sub(0, (height - circleRadius * 2.0f) / 1.2f));
        box2DCollider.setHalfSize(new Vector2f(width - 0.05f, boxHeight - 0.03f));
        box2DCollider.setOffset(new Vector2f(offset).add(0, (height - boxHeight) / 2.0f));
    }

    public CircleCollider getBottomCircle() {
        return bottomCircle;
    }

    public Box2DCollider getBox() {
        return box2DCollider;
    }
}
