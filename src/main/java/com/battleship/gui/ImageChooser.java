package com.battleship.gui;

import com.battleship.utils.BSConfigFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

class ImageChooser extends JPanel {

    JButton openButton, avatarButton;
    JFileChooser fileChooser;
    ButtonHandler buttonHandler;
    File file;


    /**
     * Constructor for the ImageChooser JPanel
     *
     * @param userAvatarButton the JButton to update
     */
    public ImageChooser(JButton userAvatarButton) {

        super(new BorderLayout());

        avatarButton = userAvatarButton;
        this.initUI();

    }

    private void initUI() {

        JFrame frame = new JFrame("Image Chooser");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        fileChooser = new JFileChooser();
        buttonHandler = new ButtonHandler();

        this.initButtons();

        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initButtons() {
        openButton = new JButton("Open an Image..");
        openButton.addActionListener(buttonHandler);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        add(buttonPanel, BorderLayout.PAGE_START);
    }

    private void updateAvatarPath(String path){
        BSConfigFile.modifyFile("Avatar_Path", path);
    }
    private void updateAvatar(File file) {
        ImageIcon ii = new ImageIcon(file.getPath());
        avatarButton.setIcon(ii);
    }

    /**
     * Private class to handle buttons.
     */
    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == openButton) {
                int returnVal = fileChooser.showOpenDialog(ImageChooser.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {

                    File file = fileChooser.getSelectedFile();
                    updateAvatar(file);
                    updateAvatarPath(file.getPath());

                }
            }
        }
    }

}
