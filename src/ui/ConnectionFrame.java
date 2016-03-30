package ui;

import account.Account;
import database.DatabaseConnection;
import main.interfaces.ConnectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class ConnectionFrame extends JFrame {
    String[] textLabels = {"Username: ", "Password: "};
    JLabel[] labels = new JLabel[2];
    JTextField[] textFields = new JTextField[2];
    JPanel[] panels = new JPanel[2];
    JButton connectButton;
    DatabaseConnection connection;
    ConnectionListener listener;

    ActionListener connectButtonClicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            connection = DatabaseConnection.getInstance(new Account(textFields[0].getText(), textFields[1].getText()));
            listener.onConnected();
            dispose();
        }
    };

    public ConnectionFrame(ConnectionListener listener) {
        super("Connection");
        setSize(300, 150);
        setVisible(true);
        init();
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.listener = listener;
    }

    private void init() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        connectButton = new JButton("Connect");
        connectButton.addActionListener(connectButtonClicked);
        connectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        for (int i = 0; i < textLabels.length; i++) {
            panels[i] = new JPanel();
            panels[i].setLayout(new FlowLayout());
            labels[i] = new JLabel(textLabels[i], JLabel.TRAILING);
            panels[i].add(labels[i]);
            textFields[i] = new JTextField(10);
            labels[i].setLabelFor(textFields[i]);
            panels[i].add(textFields[i]);
            panels[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            panels[i].setVisible(true);
            labels[i].setVisible(true);
            textFields[i].setVisible(true);
            add(panels[i]);
        }

        add(connectButton);

    }
}
