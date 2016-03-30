package ui.Components.menu;


import java.awt.event.ActionListener;
import java.util.HashMap;

public class PopUpMenuBuilder {

    private HashMap<String, ActionListener> menuMap;

    public PopUpMenuBuilder() {
        menuMap = new HashMap<>();
    }

    public PopUpMenuBuilder addItem(String title, ActionListener actionListener) {
        if (!menuMap.containsKey(title)) {
            menuMap.put(title, actionListener);
        } else {
            System.err.println(String.format("%s ignored, because there is another item with name %s", title, title));
        }
        return this;
    }

    public PopUpMenu build() {
        return new PopUpMenu(menuMap);
    }
}
