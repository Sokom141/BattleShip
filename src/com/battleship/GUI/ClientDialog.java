package com.battleship.GUI;

import com.battleship.utils.AddressChecker;

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

    public ClientDialog() {

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

        pack();
        setVisible(true);
    }

    private void onOK() {

        String ip = ipField.getText();

        try {
            int port = Integer.parseInt(portField.getText());

            if (AddressChecker.isValidIPv4(ip) && AddressChecker.isValidPort(port)) {

                SwingUtilities.invokeLater(() -> {
                    GameBoard gb = new GameBoard();
                    gb.createClient(ip, port);
                });

                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "IP or Port not valid.",
                        "Address Error",

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
