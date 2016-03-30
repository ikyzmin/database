package main;


import database.DatabaseConnection;
import database.parsers.DatabaseParser;
import main.interfaces.ConnectionListener;
import ui.ConnectionFrame;
import ui.DatabaseFrame;
import utils.UiUtils;

import java.sql.SQLException;

public class Main implements ConnectionListener{

    private DatabaseConnection connection;

    static Runnable runConnectFrame = new Runnable() {
        @Override
        public void run() {
            Main main = new Main();
            ConnectionFrame connectionFrame = new ConnectionFrame(main);
        }
    };

    static Runnable runDatabaseFrame = new Runnable() {
        @Override
        public void run() {
            try {
                Main main = new Main();
                DatabaseFrame databaseFrame = new DatabaseFrame(new DatabaseParser().parse());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };


    public static void main(String[] args) {
        UiUtils.runOnAwt(runConnectFrame);
    }

    @Override
    public void onConnected() {
        UiUtils.runOnAwt(runDatabaseFrame);
    }

    @Override
    public void onConnectedFailed(String reason) {

    }
}
