package org.example.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CustomFileChooser extends JFileChooser {
    private static final Logger logger = LoggerFactory.getLogger(CustomFileChooser.class);

    private JTextField widthField;
    private JTextField heightField;
    private JTextField spriteCountField;
    private JLabel previewLabel;
    private boolean isImportAsset;
    private JTextField encryptionKeyField;
    private boolean isDecryptingLevel;

    public CustomFileChooser(String type) {
        super();
        switch (type) {
            case "ImportAsset":
                isImportAsset = true;
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
                break;
            case "DecryptLevel":
                isDecryptingLevel = true;
                JPanel decryptPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbcLoad = new GridBagConstraints();
                gbcLoad.anchor = GridBagConstraints.WEST;
                gbcLoad.insets = new Insets(5, 5, 5, 5);

                encryptionKeyField = new JTextField();
                encryptionKeyField.setPreferredSize(new Dimension(100, 20));
                gbcLoad.gridx = 0;
                gbcLoad.gridy = 1;
                decryptPanel.add(new JLabel("Encryption Key:"), gbcLoad);
                gbcLoad.gridx = 1;
                gbcLoad.gridy = 1;
                decryptPanel.add(encryptionKeyField, gbcLoad);
                setAccessory(decryptPanel);
            default:
                logger.info("Nothing to do!");
        }

    }

    @Override
    public void approveSelection() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            logger.error("Error: Setting look and feel for UI.", e);
        }

        if(isImportAsset) {
            if (widthField.getText().isEmpty() || heightField.getText().isEmpty() || spriteCountField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields (Width, Height, Sprite Count) before saving.");
                return;
            }
        }

        if(isDecryptingLevel) {
            if (encryptionKeyField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in Encryption Key field.");
                return;
            }
        }

        if (getSelectedFile() == null) {
            JOptionPane.showMessageDialog(this, "Please select a file before opening.");
            return;
        }

        super.approveSelection();
    }

    public static CustomFileChooser windowsJFileChooser(String chooserType) {
        LookAndFeel previousLF = UIManager.getLookAndFeel();
        CustomFileChooser chooser;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            chooser = new CustomFileChooser(chooserType) {
                @Override
                protected JDialog createDialog( Component parent ) throws HeadlessException {
                    JDialog dialog = super.createDialog( parent );
                    File file = new File("assets/folder.png");
                    try {
                        BufferedImage image = ImageIO.read(file);
                        dialog.setIconImage( image );
                        return dialog;
                    } catch (IOException e) {
                        logger.error("Error: Reading image.", e);
                    }
                    return dialog;
                }
            };
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

    public String getEncryptionKeyField() {
        return encryptionKeyField.getText();
    }
}

