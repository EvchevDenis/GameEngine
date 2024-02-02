package org.example.physics2d.components;

import org.example.components.Component;
import org.example.jade.Window;
import org.joml.Vector2f;

public class BarrelBoatCollider extends Component {
    private transient CircleCollider leftBottomCircle = new CircleCollider();
    private transient CircleCollider rightBottomCircle = new CircleCollider();
    private transient Box2DCollider box2DCollider = new Box2DCollider();
    private transient boolean resetFixtureNextFrame = false;

    public float circleRadius = 0.1f;
    public Vector2f boxSize = new Vector2f(0.25f, 0.25f);
    public Vector2f offset = new Vector2f();

    @Override
    public void start() {
        this.leftBottomCircle.gameObject = this.gameObject;
        this.rightBottomCircle.gameObject = this.gameObject;
        this.box2DCollider.gameObject = this.gameObject;
        recalculateColliders();
    }

    @Override
    public void editorUpdate(float dt) {
        leftBottomCircle.editorUpdate(dt);
        rightBottomCircle.editorUpdate(dt);
        box2DCollider.editorUpdate(dt);
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

    public void setBoxSize(Vector2f newBoxSize) {
        this.boxSize = newBoxSize;
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
                Window.getPhysics().resetBarrelBoatCollider(rb, this);
            }
        }
    }

    public void recalculateColliders() {
        box2DCollider.setHalfSize(boxSize);
        box2DCollider.setOffset(new Vector2f(0, -0.03f));

        leftBottomCircle.setRadius(circleRadius);
        leftBottomCircle.setOffset(new Vector2f(offset).add(-0.07f, -0.07f));

        rightBottomCircle.setRadius(circleRadius);
        rightBottomCircle.setOffset(new Vector2f(offset).add(0.07f, -0.07f));
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
