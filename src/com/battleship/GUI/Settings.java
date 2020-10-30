package com.battleship.GUI;

import com.battleship.utils.BSConfigFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

import static com.battleship.utils.BSConfigFile.readFile;

// TODO: Maybe it's better to create an inner class that implements ActionListener
public class Settings implements ActionListener {

    private JPanel panel;
    // Warning: "Contents of collection 'map' are queried, but never updated. Maybe HashMap is not the best solution
    private final HashMap<String, Color> map = new HashMap<>();
    public File configFile; // TODO: remove?
    // private JButton b_set_name; // TODO: remove?
    private JComboBox<String> bSetColors;
    private JButton avatar_button;
    private JTextField tfNick;
    private JTextField tfWidth;
    private JTextField tfHeight;
    private JButton bExit;

    private ImageIcon user_avatar;
    private JButton bSave;

    public Settings() {
        initUI();
    }

    public void initUI() {

        JFrame frame = new JFrame("Settings");


        // TODO: use the IntelliJ GUI builder to redesign this
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(0, 1));

        // Warning: Call to 'toArray()' with pre-sized array argument 'new String[map.size()]'
        String[] colors = map.keySet().toArray(new String[map.size()]);
        bSetColors = new JComboBox<>(colors);
        bSetColors.setSelectedItem(readFile("Color"));

        bExit = new JButton("Exit");
        bSave = new JButton("Save");

        // TODO: this never changes. We should add a property to the config files and load from there.
        user_avatar = loadImage("src/resources/anonymous.png");
        avatar_button = new JButton(user_avatar);

        avatar_button.addActionListener(this);
        bExit.addActionListener(this);
        bSave.addActionListener(this);

        // TODO: refactor in a private method and maybe just one try/catch is needed.
        tfNick = new JTextField(readFile("Name"));
        tfWidth = new JTextField(readFile("Resolution_Width"));
        tfHeight = new JTextField(readFile("Resolution_Height"));

        this.addToPanel();

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private ImageIcon loadImage(String path) {

        return new ImageIcon(path);
    }

    private void addToPanel() {

        panel.add(tfNick);
        panel.add(avatar_button);
        panel.add(bSetColors);
        panel.add(tfWidth);
        panel.add(tfHeight);
        panel.add(bSave);
        panel.add(bExit);
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

            //TODO: maybe refactor in a public static method like updateConfiguration in BSConfigFile
            BSConfigFile.modifyFile("Name", newName);
            BSConfigFile.modifyFile("Color", setColor);
            BSConfigFile.modifyFile("Resolution_Width", resolutionWidth);
            BSConfigFile.modifyFile("Resolution_Height", resolutionHeight);
        }
    }

}
