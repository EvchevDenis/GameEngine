package org.example.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class CustomFileChooser extends JFileChooser {
    private final transient Logger logger = LoggerFactory.getLogger(CustomFileChooser.class);

    private JTextField widthField;
    private JTextField heightField;
    private JTextField spriteCountField;
    private JLabel previewLabel;
    private final boolean isUsingApprove;

    public CustomFileChooser(boolean isCustom) {
        super();
        isUsingApprove = isCustom;
        if (isCustom) {
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

            previewLabel = new JLabel();
            previewLabel.setPreferredSize(new Dimension(150, 100));
            panel.add(previewLabel, BorderLayout.CENTER);

            setAccessory(panel);

            this.addPropertyChangeListener(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY, evt -> {
                File selectedFile = (File) evt.getNewValue();
                if (selectedFile != null && selectedFile.isFile()) {
                    ImageIcon imageIcon = new ImageIcon(selectedFile.getPath());
                    previewLabel.setIcon(imageIcon);
                }
            });
        }
    }

    @Override
    public void approveSelection() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            logger.error("Error: Setting look and feel for UI.", e);
        }

        if(isUsingApprove) {
            if (widthField.getText().isEmpty() || heightField.getText().isEmpty() || spriteCountField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields (Width, Height, Sprite Count) before saving.");
                return;
            }
        }
        super.approveSelection();
    }

    public static CustomFileChooser windowsJFileChooser(boolean isCustom){
        LookAndFeel previousLF = UIManager.getLookAndFeel();
        CustomFileChooser chooser;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            chooser = new CustomFileChooser(isCustom);
            UIManager.setLookAndFeel(previousLF);
        } catch (IllegalAccessException | UnsupportedLookAndFeelException | InstantiationException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return chooser;
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

