package database.parsers;

import database.DatabaseBundle;
import database.DatabaseConnection;
import database.SelectionBuilder;
import database.databaseentities.JoinedTables;
import database.provider.Contract;
import database.databaseentities.Address;
import database.databaseentities.Customer;
import database.entities.Column;
import database.entities.Scheme;
import database.entities.Table;
import database.entities.Value;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;


public class DatabaseParser {
    private final DatabaseConnection connection = DatabaseConnection.getInstance();

    public DatabaseBundle parse() throws SQLException {

        ResultSet addressesSet = new SelectionBuilder().table(Contract.Addresses.TABLE_NAME).query(Contract.Addresses.ALL);
        ResultSet customersSet = new SelectionBuilder().table(Contract.Customers.TABLE_NAME).query(Contract.Customers.ALL);
        ResultSet joinedSet = new SelectionBuilder().table(Contract.Customers.TABLE_NAME).withJoin().query(Contract.Customers.JOIN_COLUMNS);
        ResultSetMetaData addressesMetaData = addressesSet.getMetaData();
        ResultSetMetaData customerMetaData = customersSet.getMetaData();
        ResultSetMetaData joinedMetaData = joinedSet.getMetaData();
        DatabaseBundle bundle = new DatabaseBundle();
        ArrayList<Column> addressesColumns = new ArrayList<>();
        ArrayList<Column> costumersColumns = new ArrayList<>();
        ArrayList<Column> joinedColumns=  new ArrayList<>();
        for (int i = 1; i <= addressesMetaData.getColumnCount(); i++) {
            addressesColumns.add(new Column(addressesMetaData.getColumnName(i)));
        }
        for (int i = 1; i < customerMetaData.getColumnCount(); i++) {
            costumersColumns.add(new Column(customerMetaData.getColumnName(i)));
        }
        for (int i = 1; i < joinedMetaData.getColumnCount(); i++) {
            joinedColumns.add(new Column(joinedMetaData.getColumnName(i)));
        }


        ResultSet foreignKeys = connection.getConnection().getMetaData().getImportedKeys(connection.getConnection().getCatalog(), null, Contract.Customers.TABLE_NAME);
        while (foreignKeys.next()) {
            costumersColumns.add(new Column(foreignKeys.getString("FKCOLUMN_NAME")));
        }
        ResultSet joinedForeignKeys = connection.getConnection().getMetaData().getImportedKeys(connection.getConnection().getCatalog(), null, Contract.Customers.TABLE_NAME);

        while (joinedForeignKeys.next()){
            joinedColumns.add(new Column(joinedForeignKeys.getString("FKCOLUMN_NAME")));
        }
        ArrayList<Value> addressValues = Address.fromCursor(addressesSet);
        ArrayList<Value> customerValues = Customer.fromCursor(customersSet);
        ArrayList<Value> joinedValues = JoinedTables.fromCursor(joinedSet);
        for (int i = 0; i < addressValues.size(); i++) {
            addressesColumns.get(i % Contract.Addresses.COLUMNS.length).addValue(addressValues.get(i));
        }

        for (int i = 0; i < customerValues.size(); i++) {
            costumersColumns.get(i % Contract.Customers.COLUMNS.length).addValue(customerValues.get(i));
        }

        for (int i=0;i<joinedValues.size();i++){
            joinedColumns.get(i%(Contract.Addresses.COLUMNS.length+Contract.Customers.COLUMNS.length)).addValue(joinedValues.get(i));
        }

        ArrayList<Table> tables = new ArrayList<>();
        tables.add(new Table(addressesMetaData.getTableName(1), addressesColumns));
        tables.add(new Table(customerMetaData.getTableName(1), costumersColumns));
        tables.add(new Table(joinedMetaData.getTableName(1),joinedColumns));
        ArrayList<Scheme> schemes = new ArrayList<>();
        schemes.add(new Scheme(addressesMetaData.getCatalogName(1), tables));
        return bundle.putSchemes(schemes).submit();

    }
}
