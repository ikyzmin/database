package database;


import java.sql.ResultSet;

public class ResultBundle {
    private int count;
    private ResultSet set;

    public ResultSet getSet() {
        return set;
    }

    public void setSet(ResultSet set) {
        this.set = set;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
