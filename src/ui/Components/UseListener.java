package ui.Components;

import javax.swing.*;
import java.awt.event.ActionListener;


public abstract class UseListener implements ActionListener {

    JComboBox comboBox;
    JTextField textField;


   public UseListener(JTextField textField,JComboBox comboBox){
       this.comboBox = comboBox;
       this.textField = textField;
   }

}
