import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.*;

public class CRUDfunctions {
    private MongoDatabase database;

    public static void main(String[] args) {
            
    }

    // Constructor to establish connection when an instance is created
    public CRUDfunctions() {
        this.database = connect();
    }

    public void insert(String apiurl, String methord, String payload, String response) {
        // Use the database instance established in the constructor
        MongoCollection<Document> ApiCollection = database.getCollection("Requests");
        
        // Get the next auto-incremented ID
        long requestId = getNextRequestId();
        
        Document document = new Document();
        document.append("request_id", requestId);
        document.append("apiurl", apiurl);
        document.append("methord", methord);
        document.append("payload", payload);
        document.append("response", response); // Added the response field
        ApiCollection.insertOne(document);
    }

    /**
     * Atomically finds and increments the request_id sequence.
     * @return The next available request ID.
     */
    private long getNextRequestId() {
        MongoCollection<Document> counters = database.getCollection("counters");
        Document filter = new Document("_id", "request_id");
        Document update = new Document("$inc", new Document("sequence_value", 1L));
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER);
        Document result = counters.findOneAndUpdate(filter, update, options);
        return result.getLong("sequence_value") - 1; // Subtract 1 to start from 0
    }

    /**
     * Retrieves the last assigned request ID from the counter.
     * This is useful for displaying the ID of a newly inserted document.
     * @return The last assigned request ID, or -1 if counter not found.
     */
    public long getLastRequestId() {
        MongoCollection<Document> counters = database.getCollection("counters");
        Document counterDoc = counters.find(eq("_id", "request_id")).first();
        return (counterDoc != null) ? counterDoc.getLong("sequence_value") - 1 : -1;
    }
    /**
     * Finds and prints the last inserted document in the collection by sorting by request_id.
     */
    public void findLastInserted() {
        MongoCollection<Document> ApiCollection = database.getCollection("Requests");
        // Sort by request_id descending to get the latest document
        Bson sort = new Document("request_id", -1);
        Document document = ApiCollection.find().sort(sort).first();
        System.out.println("--- Finding Last Inserted Document ---");
        if (document != null) {
            System.out.println(document.toJson());
        } else {
            System.out.println("No documents found in the collection.");
        }
    }

    /**
     * Finds and prints all documents in the collection.
     */
    public void findAll() {
        MongoCollection<Document> ApiCollection = database.getCollection("Requests");
        System.out.println("--- Finding All Documents ---");
        try (MongoCursor<Document> cursor = ApiCollection.find().iterator()) {
            if (!cursor.hasNext()) {
                System.out.println("No documents found in the collection.");
                return;
            }
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        }
    }

    /**
     * Finds a document by its MongoDB ObjectId string.
     * @param idString The string representation of the document's _id.
     */
    public void findById(String idString) {
        MongoCollection<Document> ApiCollection = database.getCollection("Requests");
        System.out.println("--- Finding Document by ID: " + idString + " ---");
        try {
            Document doc = ApiCollection.find(eq("_id", new ObjectId(idString))).first();
            if (doc != null) {
                System.out.println(doc.toJson());
            } else {
                System.out.println("No document found with that ID.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid ID format. Please provide a valid ObjectId string.");
        }
    }

    /**
     * Finds a document by its custom request_id.
     * @param requestId The long request_id of the document.
     * @return The found Document, or null if not found.
     */
    public Document findByRequestId(long requestId) {
        MongoCollection<Document> ApiCollection = database.getCollection("Requests");
        System.out.println("--- Finding Document by request_id: " + requestId + " ---");
        Document doc = ApiCollection.find(eq("request_id", requestId)).first();
        if (doc != null) {
            System.out.println(doc.toJson());
        } else {
            System.out.println("No document found with that request_id.");
        }
        return doc;
    }

    /**
     * Deletes a document by its MongoDB ObjectId string.
     * @param idString The string representation of the document's _id to delete.
     */
    public void deleteById(String idString) {
        MongoCollection<Document> ApiCollection = database.getCollection("Requests");
        System.out.println("--- Deleting Document by ID: " + idString + " ---");
        try {
            DeleteResult result = ApiCollection.deleteOne(eq("_id", new ObjectId(idString)));
            System.out.println("Documents deleted: " + result.getDeletedCount());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid ID format. Please provide a valid ObjectId string.");
        }
    }

    /**
     * Drops the entire database.
     */
    public void dropDatabase() {
        System.out.println("--- Deleting Database: " + database.getName() + " ---");
        database.drop();
        System.out.println("Database '" + database.getName() + "' deleted successfully.");
    }

    private MongoDatabase connect(){
        MongoConnection.testConnection();

        // Get "demo" database
        MongoDatabase database = MongoConnection.getDatabase("RestAPI_Tester");

        // Create collection "users" (only once, else it will throw error if already exists)
        try {
            database.createCollection("Requests");
            System.out.println("\nCollection 'Requests' created.\n");
        } catch (Exception e) {
            System.out.println("\nCollection 'Requests' might already exist.\n");
        }
        return database;
    }
}
