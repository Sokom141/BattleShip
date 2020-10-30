package com.battleship.GUI;

import com.battleship.Networking.IpChecker;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerDialog extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField portTextField;
    private JPanel bottomPanel;
    private JPanel serverInfo;
    private JLabel publicIPLabel;
    private JLabel portLabel;
    private JLabel publicIP;
    private JPanel buttonsPanel;
    private JCheckBox localGameCheckBox;

    private Window ref;

    public ServerDialog(Window parentWindow) {
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

        try {
            publicIP.setText(IpChecker.getIp());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ref = parentWindow;
        pack();
        setVisible(true);
    }

    private void onOK() {
        ref.port = Integer.parseInt(portTextField.getText());
        dispose();
    }

    private void onCancel() {
        dispose();
    }

}
