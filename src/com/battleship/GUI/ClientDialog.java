package com.battleship.GUI;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField ipField;
    private JTextField portField;
    private JPanel bottomPanel;
    private JPanel panel;
    private JLabel ipLabel;
    private JLabel portLabel;
    private JPanel panelButtons;

    private Window ref;

    public ClientDialog(Window parentWindow) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        ref = parentWindow;
        pack();
        setVisible(true);
    }

    private void onOK() {

        ref.ip = ipField.getText();
        ref.port = Integer.parseInt(portField.getText());

        dispose();
    }

    private void onCancel() {

        dispose();
    }
}
