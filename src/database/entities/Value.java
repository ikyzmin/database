package database.entities;

import java.sql.Types;

public class Value {
    String value;
    int type;

    public Value(String value, int type) {
        this.type = type;
        this.value = value;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return value;
    }
}
