package ui.Components;

import database.DatabaseBundle;
import database.entities.Table;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class DatabaseGrid extends JTable {

    private DatabaseBundle bundle = new DatabaseBundle();
    DefaultTableModel tableModel = new DefaultTableModel();
    Table table;

    public DatabaseGrid() {
        this(null);
    }

    public DatabaseGrid(DatabaseBundle bundle) {
        super();
        this.bundle = bundle;
        if (bundle == null || !bundle.isSubmitted()) {
            System.err.print("bundle is empty.Table Initialization failed");
        } else {
            init();
        }
    }

    private void init() {
        setRowSelectionAllowed(true);
    }

    public void setModel(DefaultTableModel model, Table table) {
        super.setModel(model);
        this.table = table;
    }

    public Table getTable() {
        return table;
    }
}
