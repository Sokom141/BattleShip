package com.battleship.GUI;

import com.battleship.utils.AddressChecker;
import com.battleship.utils.IpChecker;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;


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

    public ServerDialog() {
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
            JOptionPane.showMessageDialog(this,
                    "Unable to check the IP. Please connect to the internet.",
                    "Connection Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        pack();
        setVisible(true);
    }

    private void onOK() {

        try {
            int port = Integer.parseInt(portTextField.getText());
            if (AddressChecker.isValidPort(port)) {


                SwingUtilities.invokeLater(() -> {

                    ShipPlanner sp = new ShipPlanner();
                    sp.createServer(port);
                });

                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Port not valid.",
                        "Port Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(this,
                    "The port must be a number",
                    "Port Error",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    private void onCancel() {
        dispose();
    }

}
