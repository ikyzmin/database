package database.entities;

import java.util.ArrayList;
import java.util.List;

public class Column {
    private String name;
    private List<Value> values;

    public Column(String name){
        this.name = name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

    public List<Value> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return name;
    }

    public void addValue(Value value){
        if (values==null){
            values = new ArrayList<>();
        }
        values.add(value);
    }
}
