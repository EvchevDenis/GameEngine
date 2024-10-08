package org.example.physics2d.colliders;

import org.example.components.Component;
import org.example.jade.GameObject;
import org.example.renderer.DebugDraw;
import org.joml.Vector2f;

public class Box2DCollider extends Component {
    private Vector2f halfSize = new Vector2f(1);
    private Vector2f origin = new Vector2f();
    private Vector2f offset = new Vector2f();

    public Vector2f getOffset() {
        return this.offset;
    }

    public void setOffset(Vector2f newOffset) { this.offset.set(newOffset); }

    public Vector2f getHalfSize() {
        return halfSize;
    }

    public void setHalfSize(Vector2f halfSize) {
        this.halfSize = halfSize;
    }

    public Vector2f getOrigin() {
        return this.origin;
    }

    @Override
    public void editorUpdate(float dt, GameObject gameObject) {
        Vector2f center = new Vector2f(gameObject.transform.position).add(this.offset);
        DebugDraw.addBox2D(center, this.halfSize, gameObject.transform.rotation);
    }

    @Override
    public void editorUpdate(float dt) {
        Vector2f center = new Vector2f(this.gameObject.transform.position).add(this.offset);
        DebugDraw.addBox2D(center, this.halfSize, this.gameObject.transform.rotation);
    }
}
