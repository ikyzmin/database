package database;

import account.Account;
import database.contracts.Contract;
import ui.DatabaseFrame;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private static Account defaultAccount = new Account();
    private Account account;
    private Connection connection;

    public DatabaseConnection() {
        this(null);
    }

    public DatabaseConnection(Account account) {
        if (account == null) {
            this.account = defaultAccount;
        } else {
            this.account = account;
        }
        initConnection();
    }

    private void initConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(Contract.HOST, account.getUsername(), account.getPassword());

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    public static DatabaseConnection getInstance(Account account) {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection(account);
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }


}
