import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class App {
    public static void main(String[] args) {
        // Test connection
        MongoConnection.testConnection();

        // Get "demo" database
        MongoDatabase database = MongoConnection.getDatabase("demo");

        // Create collection "users" (only once, else it will throw error if already exists)
        try {
            database.createCollection("users");
            System.out.println("\nCollection 'users' created.\n");
        } catch (Exception e) {
            System.out.println("\nCollection might already exist: " + e.getMessage()+"\n");
        }

        // Get the collection
        MongoCollection<Document> usersCollection = database.getCollection("users");

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

        // Insert documents
        usersCollection.insertOne(user1);
        usersCollection.insertOne(user2);

        System.out.println("\nSample data inserted into 'users' collection.\n");
    }
}
