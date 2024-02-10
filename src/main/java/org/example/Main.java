package org.example;

import org.example.jade.Window;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        ToolTipManager.sharedInstance().setEnabled(false);
        Window window = Window.get();
        window.run();
    }
}
