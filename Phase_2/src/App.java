
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class App {
    public static void main(String[] args) {
        String connectionString = "mongodb://localhost:27017/";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                MongoDatabase database = mongoClient.getDatabase("demo");
                database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");

                // Create a collection
// MongoDatabase database = mongoClient.getDatabase("demo");
database.createCollection("users");

// Create sample documents
Document user1 = new Document("name", "John Doe")
        .append("age", 30)
        .append("email", "john@example.com")
        .append("address", new Document("street", "123 Main St")
                .append("city", "New York")
                .append("country", "USA"));

Document user2 = new Document("name", "Jane Smith")
        .append("age", 25)
        .append("email", "jane@example.com")
        .append("address", new Document("street", "456 Oak Ave")
                .append("city", "Los Angeles") 
                .append("country", "USA"));

// Insert documents into collection
database.getCollection("users").insertOne(user1);
database.getCollection("users").insertOne(user2);

System.out.println("Sample data inserted successfully!");                
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }
}
