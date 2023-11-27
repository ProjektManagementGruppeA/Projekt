package business;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DatabaseConnector {
    private static DatabaseConnector instance;
    private MongoClient mongoClient;
    private MongoDatabase database;

    public DatabaseConnector(String connectionString) {
        ConnectionString connString = new ConnectionString(connectionString);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .build();

        // Erstellen eines neuen MongoClient und Verbinden mit dem Server
        mongoClient = MongoClients.create(settings);

        // Etablieren einer Verbindung zur Datenbank
        database = mongoClient.getDatabase("projektmanagement");
    }

    // Statische Methode, um eine Instanz zu erhalten
    public static synchronized DatabaseConnector getInstance() {
        String connectionString = "mongodb+srv://project:ycJ3EVslOFfiCoEr@projektmanagement.wgxeir5.mongodb.net/?retryWrites=true&w=majority";
        if (instance == null) {
            instance = new DatabaseConnector(connectionString);
        }
        return instance;
    }

    // Methode, um die Datenbankverbindung zu schließen
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }

    // Getter für MongoDatabase
    public MongoDatabase getDatabase() {
        return database;
    }
}