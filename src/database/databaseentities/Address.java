package database.databaseentities;

import database.contracts.Contract;
import database.entities.Value;
import javafx.animation.KeyValue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class Address {
    public static ArrayList<Value> fromCursor(ResultSet set) throws SQLException {
        ArrayList<Value> result = new ArrayList<>();
        while (set.next()){
            for (int i = 1; i<= Contract.Addresses.COLUMNS.length; i++){
                switch (set.getMetaData().getColumnType(i)){
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
