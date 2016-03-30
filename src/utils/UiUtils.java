package utils;

import javax.swing.*;

/**
 * Created by Илья on 17.03.2016.
 */
public class UiUtils {

    public static void runOnAwt(Runnable run){
        SwingUtilities.invokeLater(run);
    }

}
