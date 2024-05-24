package org.example.editor;

import imgui.ImGui;
import org.example.components.SpriteRenderer;
import org.example.jade.GameObject;
import org.example.physics2d.colliders.*;
import org.example.renderer.PickingTexture;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

public class PropertiesWindow {
    private final List<GameObject> activeGameObjects;
    private final List<Vector4f> activeGameObjectsOgColor;
    private GameObject activeGameObject = null;
    private final PickingTexture pickingTexture;

    public PropertiesWindow(PickingTexture pickingTexture) {
        this.activeGameObjects = new ArrayList<>();
        this.pickingTexture = pickingTexture;
        this.activeGameObjectsOgColor = new ArrayList<>();
    }

    public void imgui() {
        if (activeGameObjects.size() == 1 && activeGameObjects.get(0) != null) {
            activeGameObject = activeGameObjects.get(0);
            ImGui.begin("Object Properties");

            if (ImGui.beginPopupContextWindow("ComponentAdder")) {
                if (ImGui.menuItem("Add Rigidbody")) {
                    if (activeGameObject.getComponent(Rigidbody2D.class) == null) {
                        activeGameObject.addComponent(new Rigidbody2D());
                    }
                }

                ImGui.separator();

                if (ImGui.menuItem("Add Box Collider")) {
                    if (activeGameObject.getComponent(Box2DCollider.class) == null) {
                        activeGameObject.addComponent(new Box2DCollider());
                    }
                }

                if (ImGui.menuItem("Add Circle Collider")) {
                    if (activeGameObject.getComponent(CircleCollider.class) == null) {
                        activeGameObject.addComponent(new CircleCollider());
                    }
                }

                if (ImGui.menuItem("Add BarrelBoat Collider")) {
                    if (activeGameObject.getComponent(BarrelBoatCollider.class) == null) {
                        activeGameObject.addComponent(new BarrelBoatCollider());
                    }
                }

                if (ImGui.menuItem("Add GlideBox2D Collider")) {
                    if (activeGameObject.getComponent(GlideBox2DCollider.class) == null) {
                        activeGameObject.addComponent(new GlideBox2DCollider());
                    }
                }

                if (ImGui.menuItem("Add Oval Collider")) {
                    if (activeGameObject.getComponent(OvalCollider.class) == null) {
                        activeGameObject.addComponent(new OvalCollider());
                    }
                }

                if (ImGui.menuItem("Add Pillbox Collider")) {
                    if (activeGameObject.getComponent(PillboxCollider.class) == null) {
                        activeGameObject.addComponent(new PillboxCollider());
                    }
                }

                ImGui.endPopup();
            }

            activeGameObject.imgui();
            ImGui.end();
        }
    }

    public GameObject getActiveGameObject() {
        return activeGameObjects.size() == 1 ? this.activeGameObjects.get(0) :
                null;
    }

    public List<GameObject> getActiveGameObjects() {
        return this.activeGameObjects;
    }

    public void clearSelected() {
        if (!activeGameObjectsOgColor.isEmpty()) {
            int i = 0;
            for (GameObject go : activeGameObjects) {
                SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
                if (spr != null) {
                    spr.setColor(activeGameObjectsOgColor.get(i));
                }
                i++;
            }
        }
        this.activeGameObjects.clear();
        this.activeGameObjectsOgColor.clear();
    }

    public void setActiveGameObject(GameObject go) {
        if (go != null) {
            clearSelected();
            this.activeGameObjects.add(go);
        }
    }

    public void addActiveGameObject(GameObject go) {
        SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
        if (spr != null) {
            this.activeGameObjectsOgColor.add(new Vector4f(spr.getColor()));
            spr.setColor(new Vector4f(0.8f, 0.8f, 0.0f, 0.8f));
        } else {
            this.activeGameObjectsOgColor.add(new Vector4f());
        }
        this.activeGameObjects.add(go);
    }

    public PickingTexture getPickingTexture() {
        return this.pickingTexture;
    }
}
