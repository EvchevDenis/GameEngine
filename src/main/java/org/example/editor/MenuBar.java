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

    public static void imgui() {

        ImGui.beginMenuBar();

        if(KeyListener.isKeyPressed(GLFW_KEY_LEFT_CONTROL) &&
                KeyListener.keyBeginPress(GLFW_KEY_S)) {
            EventSystem.notify(null, new Event(EventType.SaveLevel));
        }

        if(KeyListener.isKeyPressed(GLFW_KEY_LEFT_CONTROL) &&
                KeyListener.keyBeginPress(GLFW_KEY_A)) {
            EventSystem.notify(null, new Event(EventType.SaveLevelAs));
        }

        if(KeyListener.isKeyPressed(GLFW_KEY_LEFT_CONTROL) &&
                KeyListener.keyBeginPress(GLFW_KEY_O)) {
            EventSystem.notify(null, new Event(EventType.LoadLevel));
        }

        if(KeyListener.isKeyPressed(GLFW_KEY_LEFT_CONTROL) &&
                KeyListener.keyBeginPress(GLFW_KEY_F)) {
            EventSystem.notify(null, new Event(EventType.LoadLevelFrom));
        }

        if (ImGui.beginMenu("File")) {
            if (ImGui.menuItem("Save", "Ctrl+S")) {
                EventSystem.notify(null, new Event(EventType.SaveLevel));
            }

            if (ImGui.menuItem("Save As", "Ctrl+A")) {
                EventSystem.notify(null, new Event(EventType.SaveLevelAs));
            }

            if (ImGui.menuItem("Load", "Ctrl+O")) {
                EventSystem.notify(null, new Event(EventType.LoadLevel));
            }

            if (ImGui.menuItem("Load From", "Ctrl+F")) {
                EventSystem.notify(null, new Event(EventType.LoadLevelFrom));
            }

            ImGui.endMenu();
        }

        ImGui.endMenuBar();
    }
}
