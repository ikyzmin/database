package ui.Components.menu;


import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class PopUpMenu extends JPopupMenu {


    public PopUpMenu(HashMap<String,ActionListener> items){
        for (Map.Entry<String,ActionListener> entry: items.entrySet()) {
            JMenuItem item = new JMenuItem(entry.getKey());
            item.addActionListener(entry.getValue());
            add(item);
        }
    }

}
