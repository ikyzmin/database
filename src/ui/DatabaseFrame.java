package ui;

import database.*;
import database.entities.Table;
import database.parsers.DatabaseParser;
import ui.Components.DatabaseGrid;
import ui.Components.DatabaseTree;
import ui.Components.menu.PopUpMenu;
import ui.Components.menu.PopUpMenuBuilder;
import utils.UiUtils;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class DatabaseFrame extends JFrame implements TreeSelectionListener {

    DatabaseTree databaseTree;
    DatabaseGrid databaseGrid;
    JPanel treePanel;
    JPanel gridPanel;
    DatabaseBundle bundle = new DatabaseBundle();
    JButton refreshButton;
    PopUpMenu popUpMenu;

    private ActionListener refreshButtonClicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                bundle = new DatabaseParser().parse();
                databaseTree.refresh(bundle);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    };



    public DatabaseFrame(DatabaseBundle bundle) {
        super("Database");
        setSize(500, 500);
        this.bundle = bundle;
        init();
    }

    private void init() {
        FlowLayout flowLayout = new FlowLayout();
        setResizable(false);
        flowLayout.setAlignment(FlowLayout.LEADING);
        flowLayout.setHgap(0);
        flowLayout.setVgap(0);
        setLayout(new BorderLayout());
        popUpMenu = new PopUpMenuBuilder().addItem("Delete", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int row = databaseGrid.getSelectedRow();
                Table table = databaseGrid.getTable();
                deleteTableRow(row, table);
            }
        }).addItem("Insert this", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertIntoTable(databaseGrid.getTable());
            }
        }).addItem("Update", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTableRow(databaseGrid.getSelectedRow(),databaseGrid.getTable());
            }
        }).build();
        databaseTree = new DatabaseTree(bundle);
        databaseTree.addTreeSelectionListener(this);
        treePanel = new JPanel();
        gridPanel = new JPanel();
        gridPanel.setLayout(new BoxLayout(gridPanel, BoxLayout.PAGE_AXIS));
        databaseGrid = new DatabaseGrid(bundle);
        databaseGrid.setComponentPopupMenu(popUpMenu);
        treePanel.add(databaseTree);
        treePanel.setPreferredSize(new Dimension(150, 500));
        refreshButton = new JButton("refresh");
        refreshButton.addActionListener(refreshButtonClicked);
        treePanel.add(refreshButton);
        add(treePanel,BorderLayout.LINE_START);
        gridPanel.add(databaseGrid.getTableHeader());
        gridPanel.add(databaseGrid);
        gridPanel.setPreferredSize(new Dimension(350, 500));

        add(gridPanel,BorderLayout.LINE_END);
        setVisible(true);
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) databaseTree.getLastSelectedPathComponent();

        if (selectedNode == null) {
            return;
        } else {
            Object info = selectedNode.getUserObject();
            if (info instanceof Table) {
                Table selectedTable = (Table) info;
                DefaultTableModel model = new DefaultTableModel();
                for (int i = 0; i < selectedTable.getColumns().size(); i++) {
                    model.addColumn(selectedTable.getColumns().get(i), selectedTable.getColumns().get(i).getValues() != null ? selectedTable.getColumns().get(i).getValues().toArray() : new String[]{"null"});
                }
                databaseGrid.setModel(model, selectedTable);
            } else {
                return;
            }
        }
    }

    public void deleteTableRow(int row, Table table) {
        for (int i = 0; i < table.getColumns().size(); i++) {
            try {
                new SelectionBuilder().userQuery(String.format("DELETE FROM %1s WHERE %2s=%3s", table.toString(), table.getColumns().get(0), Character.isDigit(table.getColumns().get(0).getValues().get(row).toString().charAt(0))?table.getColumns().get(0).getValues().get(row).toString():("\'"+table.getColumns().get(0).getValues().get(row).toString())+"\'"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertIntoTable(Table table){
        UiUtils.runOnAwt(new Runnable() {
            @Override
            public void run() {
                InsertBuilderFrame insertBuilderFrame = new InsertBuilderFrame(table);
            }
        });
    }

    public void updateTableRow(int row,Table table){
        UiUtils.runOnAwt(new Runnable() {
            @Override
            public void run() {
                UpdateBuilderFrame updateBuilderFrame = new UpdateBuilderFrame(table,row);
            }
        });
    }


}
