package ui;

import database.entities.Table;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UpdateBuilderFrame extends JFrame {

    private Table table;
    private int row;
    private ArrayList<JPanel> panels = new ArrayList<>();

    public void UpdateBuilderFrame(Table table, int row){
        this.table = table;
        this.row = row;
        init();
    }

    private void init(){
        setVisible(true);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(100,30));
        createTextFieldWithLabel();
    }

    private void createTextFieldWithLabel(){
        for (int i = 0; i < table.getColumns().size(); i++) {
            JPanel panel = new JPanel();
            JLabel rowLabel = new JLabel(table.getColumns().get(i).getName());
            JTextField rowValue = new JTextField(table.getColumns().get(i).getValues().get(row).getValue());
            panel.setLayout(new FlowLayout());
            rowLabel.setLabelFor(rowValue);
            panel.add(rowLabel);
            panel.add(rowValue);
            panel.add(panel);
            add(panel);
            setSize(new Dimension(getSize().width,getSize().height+=50));
        }
    }



}
