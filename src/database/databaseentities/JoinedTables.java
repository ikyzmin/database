package database.databaseentities;

import database.entities.Value;
import database.provider.Contract;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class JoinedTables {

    public static ArrayList<Value> fromCursor(ResultSet set) throws SQLException {
        ArrayList<Value> result = new ArrayList<>();
        while (set.next()) {
            for (int i = 1; i <= Contract.Customers.COLUMNS.length; i++) {
                switch (set.getMetaData().getColumnType(i)) {
                    case Types.INTEGER:
                        result.add(new Value(String.valueOf(set.getInt(i)), Types.INTEGER));
                        break;
                    case Types.VARCHAR:
                        result.add(new Value(String.valueOf(set.getString(i)), Types.VARCHAR));
                        break;
                }
            }
            for (int i = Contract.Customers.COLUMNS.length+1; i <= Contract.Customers.COLUMNS.length + Contract.Addresses.COLUMNS.length; i++) {
                switch (set.getMetaData().getColumnType(i)) {
                    case Types.INTEGER:
                        result.add(new Value(String.valueOf(set.getInt(i)), Types.INTEGER));
                        break;
                    case Types.VARCHAR:
                        result.add(new Value(String.valueOf(set.getString(i)), Types.VARCHAR));
                        break;
                }
            }
        }
        return result;
    }
}
