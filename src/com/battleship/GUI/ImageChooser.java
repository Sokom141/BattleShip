package com.battleship.GUI;

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


    public ImageChooser(JButton userAvatarButton) {

        super(new BorderLayout());

        avatarButton = userAvatarButton;

        JFrame frame = new JFrame("Image Chooser");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        fileChooser = new JFileChooser();
        buttonHandler = new ButtonHandler();

        openButton = new JButton("Open an Image..");
        openButton.addActionListener(buttonHandler);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);


        add(buttonPanel, BorderLayout.PAGE_START);

        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void updateAvatar(File file) {
        ImageIcon ii = new ImageIcon(file.getPath());
        avatarButton.setIcon(ii);
    }

    /*
        public static void createAndShowGUI(ImageIcon avatar){

            JFrame frame = new JFrame("Image Chooser");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.add(new ImageChooser());

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    */
    class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == openButton) {
                int returnVal = fileChooser.showOpenDialog(ImageChooser.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {

                    File file = fileChooser.getSelectedFile();
                    updateAvatar(file);

                }
            }
        }
    }

}
