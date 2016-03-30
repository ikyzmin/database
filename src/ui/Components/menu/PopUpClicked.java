package ui.Components.menu;

import ui.Components.DatabaseGrid;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Илья on 28.03.2016.
 */
public class PopUpClicked extends MouseAdapter {

    PopUpMenu menu;

    public PopUpClicked(PopUpMenu menu){
        this.menu = menu;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        if (e.isPopupTrigger()) {
            onPop(e);
        }
    }

    private void onPop(MouseEvent e){
        if (e.getComponent() instanceof DatabaseGrid){
            DatabaseGrid grid = ((DatabaseGrid)e.getComponent());
            menu.setVisible(true);
        }
    }

}
