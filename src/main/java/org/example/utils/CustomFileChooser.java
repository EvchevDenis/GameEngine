package org.example.utils;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

public class CustomFileChooser extends JFileChooser {
    private JTextField widthField;
    private JTextField heightField;
    private JTextField spriteCountField;
    private JLabel previewLabel;

    public CustomFileChooser(boolean isCustom) {
        super();
        if(isCustom) {
            JPanel panel = new JPanel(new BorderLayout());
            JPanel fieldsPanel = new JPanel();

            fieldsPanel.add(new JLabel("Width:"));
            widthField = new JTextField();
            widthField.setPreferredSize(new Dimension(50, 20));
            fieldsPanel.add(widthField);

            fieldsPanel.add(new JLabel("Height:"));
            heightField = new JTextField();
            heightField.setPreferredSize(new Dimension(50, 20));
            fieldsPanel.add(heightField);

            fieldsPanel.add(new JLabel("Sprite Count:"));
            spriteCountField = new JTextField();
            spriteCountField.setPreferredSize(new Dimension(50, 20));
            fieldsPanel.add(spriteCountField);

            panel.add(fieldsPanel, BorderLayout.NORTH);

            // Добавление компонента для предварительного просмотра
            previewLabel = new JLabel();
            previewLabel.setPreferredSize(new Dimension(150, 100));
            panel.add(previewLabel, BorderLayout.CENTER);

            setAccessory(panel);

            // Добавление слушателя для события выбора файла
            this.addPropertyChangeListener(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY, evt -> {
                File selectedFile = (File) evt.getNewValue();
                if (selectedFile != null && selectedFile.isFile()) {
                    ImageIcon imageIcon = new ImageIcon(selectedFile.getPath());
                    previewLabel.setIcon(imageIcon);
                }
            });
        }
    }
    public String getWidthValue() {
        return widthField.getText();
    }

    public String getHeightValue() {
        return heightField.getText();
    }

    public String getSpriteCountValue() {
        return spriteCountField.getText();
    }
}

