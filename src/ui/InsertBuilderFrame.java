package ui;

import database.SelectionBuilder;
import database.entities.Table;
import ui.Components.UseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;


public class InsertBuilderFrame extends JFrame {

    private Table table;
    private int count;
    private ArrayList<String> columns;
    private StringBuilder query;
    private StringBuilder values;

    public InsertBuilderFrame(Table table) {
        this.table = table;
        count = table.getColumns().size();
        columns = new ArrayList<>(count);
        for (int i = 0; i < table.getColumns().size(); i++) {
            columns.add(table.getColumns().get(i).getName());
        }
        query = new StringBuilder();
        values = new StringBuilder();
        init();
    }

    private void init() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        add(createBuilderPanel(null));
        setVisible(true);
        pack();

    }

    private JPanel createBuilderPanel(String previous) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JComboBox comboBox;
        if (previous == null) {
            comboBox = new JComboBox(columns.toArray());
            panel.add(comboBox);
        } else {
            columns.remove(previous);
            comboBox = new JComboBox(columns.toArray());
            panel.add(comboBox);
        }
        JTextField valueField = new JTextField();
        valueField.setPreferredSize(new Dimension(100,30));
        JButton useButton = new JButton("Use");
        useButton.addActionListener(new UseListener(valueField, comboBox) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count == table.getColumns().size()) {
                    query.append(String.format("INSERT INTO %s (%s, ", table.toString(), comboBox.getSelectedItem().toString()));
                    values.append(String.format("VALUES(%s, ", valueField.getText()));
                    count--;
                    panel.setEnabled(false);
                    InsertBuilderFrame.this.add(createBuilderPanel(comboBox.getSelectedItem().toString()));
                } else {
                    if (count == 1) {
                        query.append(String.format("%s) ", comboBox.getSelectedItem().toString()));
                        values.append(String.format("%s);", valueField.getText()));
                        try {
                            new SelectionBuilder().userQuery(query.append(values).toString());
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        dispose();
                    } else {
                        query.append(String.format("%s, ", comboBox.getSelectedItem().toString()));
                        values.append(String.format("%s, ", Character.isDigit(valueField.getText().charAt(0))?valueField.getText():String.format("\'%s\'",valueField.getText())));
                        count--;
                        panel.setEnabled(false);
                        InsertBuilderFrame.this.add(createBuilderPanel(comboBox.getSelectedItem().toString()));
                    }
                }
            }
        });
        panel.add(valueField);
        panel.add(useButton);
        setSize(new Dimension(getSize().width,getSize().height+=50));
        return panel;
    }
}
