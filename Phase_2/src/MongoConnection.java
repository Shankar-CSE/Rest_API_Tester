import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import org.bson.Document;

public class MongoConnection {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017/";
    private static MongoClient mongoClient;

    // Initialize MongoClient only once
    static {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(CONNECTION_STRING))
                .serverApi(serverApi)
                .build();

        mongoClient = MongoClients.create(settings);
    }

    // Method to get database
    public static MongoDatabase getDatabase(String dbName) {
        return mongoClient.getDatabase(dbName);
    }

    // Optional: ping method to test connection
    public static void testConnection() {
        MongoDatabase database = getDatabase("demo");
        database.runCommand(new Document("ping", 1));
        System.out.println("\nConnection successful: Pinged MongoDB!\n");
    }
}
