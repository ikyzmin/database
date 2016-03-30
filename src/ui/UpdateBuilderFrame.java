package ui;

import database.SelectionBuilder;
import database.entities.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class UpdateBuilderFrame extends JFrame {

    private Table table;
    private int row;
    private ArrayList<JPanel> panels = new ArrayList<>();

    public UpdateBuilderFrame(Table table, int row){
        this.table = table;
        this.row = row;
        init();
    }

    private void init(){
        setVisible(true);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setSize(new Dimension(100,50));
        createTextFieldWithLabel();
    }

    private void createTextFieldWithLabel(){
        for (int i = 0; i < table.getColumns().size(); i++) {
            JPanel panel = new JPanel();
            JLabel rowLabel = new JLabel(table.getColumns().get(i).getName());
            JTextField rowValue = new JTextField(table.getColumns().get(i).getValues().get(row).getValue(),JLabel.TRAILING);
            panel.setLayout(new FlowLayout());
            rowLabel.setLabelFor(rowValue);
            panel.add(rowLabel);
            panel.add(rowValue);
            panels.add(panel);
            add(panel);
            setSize(new Dimension(getSize().width,getSize().height+=50));
        }
        Button update = new Button("Update");
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder query = new StringBuilder(String.format("UPDATE %s SET ",table.toString()));
                for (int i = 0; i < panels.size(); i++) {
                    JTextField text = ((JTextField)panels.get(i).getComponent(1));
                    if (i==panels.size()-1) {
                        query.append(String.format("%s=%s; ", table.getColumns().get(i).getName(), text.getText()));
                    }else{
                        query.append(String.format("%s=%s, ", table.getColumns().get(i).getName(), text.getText()));
                    }
                }
                try {
                    new SelectionBuilder().userQuery(query.toString());
                    dispose();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        setSize(new Dimension(getSize().width,getSize().height+=50));
        add(update);
    }



}
