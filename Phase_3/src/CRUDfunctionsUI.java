import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class CRUDfunctionsUI {
    private MongoDatabase database;

    public CRUDfunctionsUI() {
        this.database = connect();
    }

    public void insert(String apiurl, String methord, String payload, String response) {
        MongoCollection<Document> ApiCollection = database.getCollection("Requests");
        
        long requestId = getNextRequestId();
        
        Document document = new Document();
        document.append("request_id", requestId);
        document.append("apiurl", apiurl);
        document.append("methord", methord);
        document.append("payload", payload);
        document.append("response", response);
        ApiCollection.insertOne(document);
    }

    private long getNextRequestId() {
        MongoCollection<Document> counters = database.getCollection("counters");
        Document filter = new Document("_id", "request_id");
        Document update = new Document("$inc", new Document("sequence_value", 1L));
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER);
        Document result = counters.findOneAndUpdate(filter, update, options);
        return result.getLong("sequence_value") - 1;
    }

    public long getLastRequestId() {
        MongoCollection<Document> counters = database.getCollection("counters");
        Document counterDoc = counters.find(eq("_id", "request_id")).first();
        return (counterDoc != null) ? counterDoc.getLong("sequence_value") - 1 : -1;
    }

    public String findLastInserted() {
        MongoCollection<Document> ApiCollection = database.getCollection("Requests");
        Bson sort = new Document("request_id", -1);
        Document document = ApiCollection.find().sort(sort).first();
        if (document != null) {
            return formatDocument(document);
        } else {
            return "No documents found in the collection.";
        }
    }

    public String findAll() {
        MongoCollection<Document> ApiCollection = database.getCollection("Requests");
        StringBuilder result = new StringBuilder();
        try (MongoCursor<Document> cursor = ApiCollection.find().iterator()) {
            if (!cursor.hasNext()) {
                return "No documents found in the collection.";
            }
            while (cursor.hasNext()) {
                result.append(formatDocument(cursor.next())).append("\n" + "=".repeat(50) + "\n\n");
            }
        }
        return result.toString();
    }

    public String findByRequestId(long requestId) {
        MongoCollection<Document> ApiCollection = database.getCollection("Requests");
        Document doc = ApiCollection.find(eq("request_id", requestId)).first();
        if (doc != null) {
            return formatDocument(doc);
        } else {
            return "No document found with that request_id.";
        }
    }
    
    private String formatDocument(Document doc) {
        StringBuilder formatted = new StringBuilder();
        formatted.append("Request ID: ").append(doc.get("request_id")).append("\n");
        formatted.append("URL: ").append(doc.getString("apiurl")).append("\n");
        formatted.append("Method: ").append(doc.getString("methord")).append("\n");
        formatted.append("Payload: ").append(doc.getString("payload").isEmpty() ? "None" : doc.getString("payload")).append("\n");
        formatted.append("Response: \n").append(doc.getString("response")).append("\n");
        return formatted.toString();
    }

    private MongoDatabase connect(){
        MongoConnection.testConnection();
        MongoDatabase database = MongoConnection.getDatabase("RestAPI_Tester");
        try {
            database.createCollection("Requests");
        } catch (Exception e) {
            // Collection already exists
        }
        return database;
    }
}