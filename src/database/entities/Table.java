package database.entities;

import java.util.List;

/**
 * Created by Илья on 16.03.2016.
 */
public class Table {

    private String name;
    private List<Column> columns;

    public Table(String name,List<Column> columns){
        this.name= name;
        this.columns =columns;
    }

    @Override
    public String toString() {
        return name;
    }

    public List<Column> getColumns() {
        return columns;
    }
}
