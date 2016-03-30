package database;

import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;

public class DatabaseObserver implements DatabaseChangeListener {

    DatabaseListener listener;

    public DatabaseObserver(DatabaseListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDatabaseChangeNotification(DatabaseChangeEvent databaseChangeEvent) {
        listener.onChanged();
    }
}
