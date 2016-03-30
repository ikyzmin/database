package database.entities;

import java.util.List;

public class Scheme {
    private String name;
    private List<Table> tables;

    public List<Table> getTables() {
        return tables;
    }

    public String getName() {
        return name;
    }

    public Scheme(String name, List<Table> tables) {
        this.tables = tables;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    @Override
    public String toString() {
        return name;
    }
}
