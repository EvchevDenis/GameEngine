package org.example.editor;

import imgui.ImGui;
import org.example.jade.KeyListener;
import org.example.observers.EventSystem;
import org.example.observers.events.Event;
import org.example.observers.events.EventType;

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

        if(KeyListener.isKeyPressed(GLFW_KEY_LEFT_CONTROL) &&
                KeyListener.keyBeginPress(GLFW_KEY_N)) {
            EventSystem.notify(null, new Event(EventType.CreateNewLevel));
        }

        if(KeyListener.isKeyPressed(GLFW_KEY_LEFT_CONTROL) &&
                KeyListener.keyBeginPress(GLFW_KEY_L)) {
            EventSystem.notify(null, new Event(EventType.EncryptCurrentLevel));
        }

        if(KeyListener.isKeyPressed(GLFW_KEY_LEFT_CONTROL) &&
                KeyListener.keyBeginPress(GLFW_KEY_E)) {
            EventSystem.notify(null, new Event(EventType.EncryptLevel));
        }

        if(KeyListener.isKeyPressed(GLFW_KEY_LEFT_CONTROL) &&
                KeyListener.keyBeginPress(GLFW_KEY_D)) {
            EventSystem.notify(null, new Event(EventType.DecryptLevel));
        }

        if (ImGui.beginMenu("File")) {
            if (ImGui.menuItem("New Level", "Ctrl+N")) {
                EventSystem.notify(null, new Event(EventType.CreateNewLevel));
            }

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

            if (ImGui.menuItem("Encrypt Current Level", "Ctrl+L")) {
                EventSystem.notify(null, new Event(EventType.EncryptCurrentLevel));
            }

            if (ImGui.menuItem("Encrypt Level", "Ctrl+E")) {
                EventSystem.notify(null, new Event(EventType.EncryptLevel));
            }

            if (ImGui.menuItem("Decrypt Level", "Ctrl+D")) {
                EventSystem.notify(null, new Event(EventType.DecryptLevel));
            }

            ImGui.endMenu();
        }

        ImGui.endMenuBar();
    }
}
