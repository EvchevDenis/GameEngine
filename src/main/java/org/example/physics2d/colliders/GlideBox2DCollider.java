package org.example.physics2d.colliders;

import org.example.components.Component;
import org.example.jade.GameObject;
import org.example.jade.Window;
import org.joml.Vector2f;

public class GlideBox2DCollider extends Component {
    private transient CircleCollider leftUpperCircle = new CircleCollider();
    private transient CircleCollider rightUpperCircle = new CircleCollider();
    private transient CircleCollider leftBottomCircle = new CircleCollider();
    private transient CircleCollider rightBottomCircle = new CircleCollider();
    private transient Box2DCollider box2DCollider = new Box2DCollider();
    private transient boolean resetFixtureNextFrame = false;

    public float circleRadius = 0.1f;
    public Vector2f boxSize = new Vector2f(0.25f, 0.25f);
    public Vector2f offset = new Vector2f();

    @Override
    public void start() {
        this.leftUpperCircle.gameObject = this.gameObject;
        this.rightUpperCircle.gameObject = this.gameObject;
        this.leftBottomCircle.gameObject = this.gameObject;
        this.rightBottomCircle.gameObject = this.gameObject;
        this.box2DCollider.gameObject = this.gameObject;
        recalculateColliders();
    }

    @Override
    public void editorUpdate(float dt) {
        leftUpperCircle.editorUpdate(dt, this.gameObject);
        rightUpperCircle.editorUpdate(dt, this.gameObject);
        leftBottomCircle.editorUpdate(dt, this.gameObject);
        rightBottomCircle.editorUpdate(dt, this.gameObject);
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

    public void setCircleRadius(float newVal) {
        this.circleRadius = newVal;
        recalculateColliders();
        resetFixture();
    }

    public void setBoxSize(float x, float y) {
        this.boxSize = new Vector2f(x, y);
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
                Window.getPhysics().resetGlideBoxCollider(rb, this);
            }
        }
    }

    public void recalculateColliders() {
        box2DCollider.setHalfSize(boxSize);

        leftUpperCircle.setRadius(circleRadius);
        leftUpperCircle.setOffset(new Vector2f(offset).add(-0.1f, 0.1f));

        rightUpperCircle.setRadius(circleRadius);
        rightUpperCircle.setOffset(new Vector2f(offset).add(0.1f, 0.1f));

        leftBottomCircle.setRadius(circleRadius);
        leftBottomCircle.setOffset(new Vector2f(offset).add(-0.1f, -0.1f));

        rightBottomCircle.setRadius(circleRadius);
        rightBottomCircle.setOffset(new Vector2f(offset).add(0.1f, -0.1f));

    }

    public CircleCollider getLeftUpperCircle() {
        return leftUpperCircle;
    }

    public CircleCollider getRightUpperCircle() {
        return rightUpperCircle;
    }

    public CircleCollider getLeftBottomCircle() {
        return leftBottomCircle;
    }

    public CircleCollider getRightBottomCircle() {
        return rightBottomCircle;
    }

    public Box2DCollider getBox2DCollider() {
        return box2DCollider;
    }

}
