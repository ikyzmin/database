package ui.Components;

import database.DatabaseBundle;
import database.entities.Column;
import database.entities.Scheme;
import database.entities.Table;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;


public class DatabaseTree extends JTree {

    private DatabaseBundle bundle;
    private static DefaultMutableTreeNode HEAD = new DefaultMutableTreeNode();
    private static DefaultTreeModel model = new DefaultTreeModel(HEAD);




    public DatabaseTree() {
        this(null);
    }

    public DatabaseTree(DatabaseBundle bundle) {
        super(HEAD);
        this.bundle = bundle;
        if (bundle == null || !bundle.isSubmitted()) {
            System.out.print("WARNING! Your tree is empty. Initialization failed");
        }else {
            init();
        }
    }

    public void refresh(DatabaseBundle bundle){
        this.bundle = bundle;
        if (bundle == null || !bundle.isSubmitted()) {
            System.out.print("WARNING! Your tree is empty. Initialization failed");
        }else {
            setModel(null);
            HEAD = new DefaultMutableTreeNode();
            init();
        }
    }

    private void init() {
        setRootVisible(false);

        for (Scheme scheme:bundle.getSchemes()) {
            DefaultMutableTreeNode treeScheme = new DefaultMutableTreeNode(scheme);
            for(Table table:scheme.getTables()){
                DefaultMutableTreeNode treeTable = new DefaultMutableTreeNode(table);
                for(Column column :table.getColumns()){
                    treeTable.add(new DefaultMutableTreeNode(column));
                }
                treeScheme.add(treeTable);
            }
            HEAD.add(treeScheme);
            model.setRoot(HEAD);
            setModel(model);
        }
    }


}
