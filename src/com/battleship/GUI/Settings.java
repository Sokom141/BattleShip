package com.battleship.GUI;


import com.battleship.utils.BSConfigFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static com.battleship.utils.BSConfigFile.readFile;
import static com.battleship.utils.BSConfigFile.readProperties;

public class Settings implements ActionListener{

    private JFrame frame;
    private JPanel panel;
    // private JButton b_set_name;
    private JComboBox<String> bSetColors;
    private JButton bExit;
    private JTextField tfNick;
    private JButton avatar_button;
    private JTextField tfWidth;
    private JTextField tfHeight;
    public File configFile;
    private JButton bSave;
    private ImageIcon user_avatar;
    private HashMap<String, Color> map = new HashMap<>();
    private String[] colors;

    public Settings() throws IOException {
        initUI();
    }

    public void initUI() throws IOException {

        frame = new JFrame();
        frame.setTitle("Settings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(0, 1));

        CreateColorPalette.initHashMap(map);
        colors = map.keySet().toArray(new String[map.size()]);
        bSetColors = new JComboBox<String>(colors);
        bSetColors.setSelectedItem(readFile("Color"));
        frame.add(bSetColors);

        bExit = new JButton("Exit");
        bSave = new JButton("Save");
        user_avatar = loadImage("src/resources/anonymous.png");
        avatar_button = new JButton(user_avatar);

        avatar_button.addActionListener(this);
        bExit.addActionListener(this);
        bSave.addActionListener(this);

        try {
            tfNick = new JTextField(readFile("Name"));
        } catch (IOException ex) {
            tfNick = new JTextField("Enter your Nickname");
        }
        try {
            tfWidth = new JTextField(readFile("Resolution_Width"));
            tfHeight = new JTextField(readFile("Resolution_Height"));
        } catch (IOException ex) {
            tfWidth = new JTextField("1000");
            tfHeight = new JTextField("500");
        }

        panel.add(tfNick);
        panel.add(avatar_button);
        panel.add(bSetColors);
        panel.add(tfWidth);
        panel.add(tfHeight);
        panel.add(bSave);
        panel.add(bExit);


        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        /*
        Test for colors
        String value = BSConfigFile.readProperties("Color");

        for(int i=0;i<value.length;i++){
        System.out.println(value);}
         } */
    }
        private ImageIcon loadImage(String path) {

            return new ImageIcon(path);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();


            if (source == bExit){
                System.exit(0);
            } else if (source == avatar_button) {
                SwingUtilities.invokeLater(() -> {
                    ImageChooser imageChooser = new ImageChooser(avatar_button);
                });
            } else if (source == bSave) {
                String newName = tfNick.getText();
                String setColor = (String) bSetColors.getSelectedItem();
                String resolutionWidth = tfWidth.getText();
                String resolutionHeight = tfHeight.getText();
                try {
                    BSConfigFile.modifyFile("Name", newName);
                    BSConfigFile.modifyFile("Color", setColor);
                    BSConfigFile.modifyFile("Resolution_Width", resolutionWidth);
                    BSConfigFile.modifyFile("Resolution_Height", resolutionHeight);

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

        }
}
