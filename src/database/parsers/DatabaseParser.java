package database.parsers;

import database.DatabaseBundle;
import database.DatabaseConnection;
import database.SelectionBuilder;
import database.contracts.Contract;
import database.databaseentities.Address;
import database.databaseentities.Customer;
import database.entities.Column;
import database.entities.Scheme;
import database.entities.Table;
import database.entities.Value;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;


public class DatabaseParser {
    private final DatabaseConnection connection = DatabaseConnection.getInstance();

    public DatabaseBundle parse() throws SQLException {

        ResultSet addressesSet = new SelectionBuilder().table(Contract.Addresses.TABLE_NAME).query(Contract.Addresses.ALL);
        ResultSet customersSet = new SelectionBuilder().table(Contract.Customers.TABLE_NAME).query(Contract.Customers.ALL);
        ResultSetMetaData addressesMetaData = addressesSet.getMetaData();
        ResultSetMetaData customerMetaData = customersSet.getMetaData();
        DatabaseBundle bundle = new DatabaseBundle();
        ArrayList<Column> addressesColumns = new ArrayList<>();
        ArrayList<Column> costumersColumns = new ArrayList<>();
        for (int i = 1; i <= addressesMetaData.getColumnCount(); i++) {
            addressesColumns.add(new Column(addressesMetaData.getColumnName(i)));
        }
        for (int i = 1; i < customerMetaData.getColumnCount(); i++) {
            costumersColumns.add(new Column(customerMetaData.getColumnName(i)));
        }
        ResultSet foreignKeys = connection.getConnection().getMetaData().getImportedKeys(connection.getConnection().getCatalog(), null, Contract.Customers.TABLE_NAME);
        while (foreignKeys.next()) {
            costumersColumns.add(new Column(foreignKeys.getString("FKCOLUMN_NAME")));
        }
        ArrayList<Value> addressValues = Address.fromCursor(addressesSet);
        ArrayList<Value> customerValues = Customer.fromCursor(customersSet);
        for (int i = 0; i < addressValues.size(); i++) {
            addressesColumns.get(i % Contract.Addresses.COLUMNS.length).addValue(addressValues.get(i));
        }

        for (int i = 0; i < customerValues.size(); i++) {
            costumersColumns.get(i % Contract.Customers.COLUMNS.length).addValue(customerValues.get(i));
        }

        ArrayList<Table> tables = new ArrayList<>();
        tables.add(new Table(addressesMetaData.getTableName(1), addressesColumns));
        tables.add(new Table(customerMetaData.getTableName(1), costumersColumns));
        ArrayList<Scheme> schemes = new ArrayList<>();
        schemes.add(new Scheme(addressesMetaData.getCatalogName(1), tables));
        return bundle.putSchemes(schemes).submit();

    }
}
