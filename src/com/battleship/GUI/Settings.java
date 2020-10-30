package com.battleship.GUI;

import com.battleship.utils.BSConfigFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO: Maybe it's better to create an inner class that implements ActionListener
public class Settings implements ActionListener {

    private JPanel panel;
    private JTextField tfNick;
    private JButton avatar_button;
    private JTextField tfWidth;
    private JTextField tfHeight;
    private JButton bSave;
    private JButton bExit;
    private JLabel labelUserName;
    private JLabel labelImage;
    private JLabel labelWRes;
    private JLabel labelHRes;


    private JComboBox<String> bSetColors;
    final String[] colorsPalette = new String[]{"BLUE","MAGENTA","RED","ORANGE","BLACK","GREEN"};
    public Settings() {
        initUI();
    }

    public void initUI() {

        JFrame frame = new JFrame("Settings");

        avatar_button.addActionListener(this);
        bExit.addActionListener(this);
        bSave.addActionListener(this);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private ImageIcon loadImage(String path) {

        return new ImageIcon(path);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == bExit) {
            System.exit(0);
        } else if (source == avatar_button) {
            SwingUtilities.invokeLater(() -> new ImageChooser(avatar_button));
        } else if (source == bSave) {
            String newName = tfNick.getText();
            String setColor = (String) bSetColors.getSelectedItem();
            String resolutionWidth = tfWidth.getText();
            String resolutionHeight = tfHeight.getText();

            BSConfigFile.updateConfiguration(newName, setColor, resolutionWidth,resolutionHeight);
        }
    }


    private void createUIComponents() {

        tfNick = new JTextField(BSConfigFile.readProperties("Name"));
        avatar_button = new JButton(loadImage(BSConfigFile.readProperties("Avatar_Path")));
        bSetColors = new JComboBox<>(colorsPalette);
        bSetColors.setSelectedItem(BSConfigFile.readProperties("Color"));
        tfWidth = new JTextField(BSConfigFile.readProperties("Resolution_Width"));
        tfHeight = new JTextField(BSConfigFile.readProperties("Resolution_Height"));
    }
}
