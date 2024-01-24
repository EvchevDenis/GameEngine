package org.example.editor;

import imgui.ImGui;
import org.example.jade.KeyListener;
import org.example.observers.EventSystem;
import org.example.observers.events.Event;
import org.example.observers.events.EventType;

import javax.swing.*;
import java.io.File;

import static org.lwjgl.glfw.GLFW.*;

public class MenuBar {

    public void imgui() {

        ImGui.beginMenuBar();

        if(KeyListener.isKeyPressed(GLFW_KEY_LEFT_CONTROL) &&
                KeyListener.keyBeginPress(GLFW_KEY_S)) {
            EventSystem.notify(null, new Event(EventType.SaveLevel));
        }

        if(KeyListener.isKeyPressed(GLFW_KEY_LEFT_CONTROL) &&
                KeyListener.keyBeginPress(GLFW_KEY_O)) {
            EventSystem.notify(null, new Event(EventType.LoadLevel));
        }

        if (ImGui.beginMenu("File")) {
            if (ImGui.menuItem("Save", "Ctrl+S")) {
                EventSystem.notify(null, new Event(EventType.SaveLevel));
            }

            if (ImGui.menuItem("Load", "Ctrl+O")) {
                EventSystem.notify(null, new Event(EventType.LoadLevel));
            }

            ImGui.endMenu();
        }

        ImGui.endMenuBar();
    }
}
