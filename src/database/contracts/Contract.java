package database.contracts;


public interface Contract {

    String HOST = "jdbc:mysql://localhost:3306/lab4";

    interface  Addresses{
        String TABLE_NAME = "adresses";
        String ID = "id";
        String ADDRESS = "adress";
        String TIME = "time";
        String ALL = "*";
        String [] COLUMNS = new String[]{ID,ADDRESS,TIME};
    }

    interface  Customers{
        String TABLE_NAME = "customers";
        String ID = "id";
        String NAME = "name";
        String ADDRESS_ID = "adress_id";
        String ALL = "*";
        String [] COLUMNS = new String[]{ID,NAME,ADDRESS_ID};

    }

}
