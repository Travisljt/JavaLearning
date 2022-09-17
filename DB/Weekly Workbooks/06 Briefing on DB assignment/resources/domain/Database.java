package domain;

public final class Database {
    private String databaseName;

    public Database(String databaseName) {
        setDatabaseName(databaseName);
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    
}
