package org.example.editor;

import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.ImString;
import org.example.jade.GameObject;
import org.example.jade.Window;

import java.util.List;

public class SceneHierarchyWindow {

    private static String payloadDragDropType = "SceneHierarchy";
    private ImString searchQuery = new ImString(256);
    private boolean searchRequested = false;

    public void imgui() {
        ImGui.begin("Scene Hierarchy");

        ImGui.inputTextWithHint(" ","Type object name", searchQuery, ImGuiInputTextFlags.None);
        ImGui.sameLine();
        if (ImGui.button("Search")) {
            searchRequested = true;
        }

        List<GameObject> gameObjects = Window.getScene().getGameObjects();
        int index = 0;
        int targetIndex = -1;

        ImGui.text("Total objects on scene: " + gameObjects.size());

        ImGui.beginChild("ScrollableRegion", 0, 0, true);
        for (GameObject obj : gameObjects) {
            if (!obj.doSerialization()) {
                continue;
            }

            ImGui.separator();
            boolean treeNodeOpen = doTreeNode(obj, index);
            if (treeNodeOpen) {
                ImGui.treePop();
            }
            ImGui.separator();

            if(searchRequested && !searchQuery.get().isEmpty()) {
                if (obj.name.contains(searchQuery.get())) {
                    targetIndex = index;
                }
            }

            index++;
        }

        if (searchRequested && targetIndex != -1) {
            float scrollPosition = (float) targetIndex / gameObjects.size() * ImGui.getScrollMaxY() + 5;
            ImGui.setScrollY(scrollPosition);
            searchRequested = false;
        }

        ImGui.endChild();

        ImGui.end();
    }

    private boolean doTreeNode(GameObject obj, int index) {
        ImGui.pushID(index);
        boolean treeNodeOpen = ImGui.treeNodeEx(
                obj.name,
                ImGuiTreeNodeFlags.DefaultOpen |
                        ImGuiTreeNodeFlags.FramePadding |
                        ImGuiTreeNodeFlags.OpenOnArrow |
                        ImGuiTreeNodeFlags.SpanAvailWidth,
                obj.name
        );

        if(!treeNodeOpen) {
            obj.imgui();
        }

        ImGui.popID();

        if (ImGui.beginDragDropSource()) {
            ImGui.setDragDropPayload(payloadDragDropType, obj);
            ImGui.text(obj.name);
            ImGui.endDragDropSource();
        }

        if (ImGui.beginDragDropTarget()) {
            Object payloadObj = ImGui.acceptDragDropPayload(payloadDragDropType);
            if (payloadObj != null) {
                if (payloadObj.getClass().isAssignableFrom(GameObject.class)) {
                    GameObject playerGameObj = (GameObject)payloadObj;
                    System.out.println("Payload accepted '" + playerGameObj.name + "'");
                }
            }
            ImGui.endDragDropTarget();
        }

        return treeNodeOpen;
    }
}
