package database.provider;


import database.databaseentities.Address;

public interface Contract {

    String HOST = "jdbc:mysql://localhost:3306/lab4";

    interface Addresses {
        String TABLE_NAME = "adresses";
        String ID = "id";
        String ADDRESS = "adress";
        String TIME = "time";
        String ALL = "*";
        String[] COLUMNS = new String[]{ID, ADDRESS, TIME};
        String JOIN_COLUMN = TABLE_NAME+"."+ID+", "+TABLE_NAME+"."+ADDRESS+", "+TABLE_NAME+"."+TIME;
    }

    interface Customers {
        String TABLE_NAME = "customers";
        String ID = "id";
        String NAME = "name";
        String ADDRESS_ID = "adress_id";
        String ALL = "*";
        String[] COLUMNS = new String[]{ID, NAME, ADDRESS_ID};
        String[] JOINED_COLUMNS = new String[]{ID,NAME,ADDRESS_ID,Addresses.ID,Addresses.ADDRESS,Addresses.TIME};
        String JOIN_COLUMNS = TABLE_NAME+"."+ID+", "+TABLE_NAME+"."+NAME+", "+TABLE_NAME+"."+ADDRESS_ID+", "+ Addresses.JOIN_COLUMN;
        String TABLE_INNER_JOIN = "INNER JOIN " + Addresses.TABLE_NAME + " ON " + Addresses.TABLE_NAME + "." + Addresses.ID + "=" + TABLE_NAME + "." + ADDRESS_ID;

    }

}
