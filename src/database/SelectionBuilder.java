package database;

import database.provider.Contract;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectionBuilder {

    private Statement statement;
    private Connection connection;
    private StringBuilder query = new StringBuilder("select ");
    private StringBuilder table = new StringBuilder();
    private StringBuilder where = new StringBuilder();
    private StringBuilder order = new StringBuilder();
    private StringBuilder columns = new StringBuilder();
    private ResultSet resultSet;
    private boolean withJoin;


    public SelectionBuilder table(String name) {
        this.table.append(name + " ");
        return this;
    }

    public SelectionBuilder whereEq(String whereClause, int whereValue) {
        this.where.append("WHERE " + whereClause).append(" = ").append(whereValue);
        return this;
    }

    public SelectionBuilder orderedBy(OrderType type, String column) {
        this.order.append("ORDERED BY ").append(column).append(type.equals(OrderType.ASC) ? " ASC" : " DESC");
        return this;
    }

    public SelectionBuilder withJoin(){
        this.withJoin = true;
        return this;
    }


    public ResultSet query(String... columns) {
        StringBuilder queryColumns = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            queryColumns.append(columns[i]).append(i == (columns.length - 1) ? " " : ", ");
        }
        query.append(queryColumns).append(" FROM ").append(table).append(where).append(order);
        if (withJoin){
            query.append(Contract.Customers.TABLE_INNER_JOIN);
        }
        connection = DatabaseConnection.getInstance().getConnection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query.toString());
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getQuery(String... columns) {
        StringBuilder queryColumns = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            queryColumns.append(columns[i]).append(i == (columns.length - 1) ? " " : ", ");
        }
        query.append(queryColumns).append(" FROM ").append(table).append(where).append(order);
        return query.toString();
    }

    public ResultBundle userQuery(String query) throws SQLException {
        connection = DatabaseConnection.getInstance().getConnection();
        statement = connection.createStatement();
        query = query.toLowerCase();
        int count=0;
        ResultSet set=null;
        if (query.contains("delete") || query.contains("insert") || query.contains("update")) {
             count = statement.executeUpdate(query);
        }else{
            set = statement.executeQuery(query);
        }
        ResultBundle resultBundle = new ResultBundle();
        resultBundle.setCount(count);
        resultBundle.setSet(set);
        return resultBundle;
    }


}
