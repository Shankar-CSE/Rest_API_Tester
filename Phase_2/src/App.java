import org.bson.Document;
import org.bson.types.ObjectId;

public class App {
    public static void main(String[] args) {
        // Create an instance of CRUDfunctions. The constructor will handle the database connection.
        CRUDfunctions crudFunctions = new CRUDfunctions();

        System.out.println("=== CRUD Functions Test Suite ===");

        // 1. INSERT: Insert a few sample documents
        System.out.println("\n--- 1. Testing INSERT ---");
        crudFunctions.insert("http://test.com/api/users", "GET", "", "{\"status\":\"200 OK\"}");
        crudFunctions.insert("http://test.com/api/products", "POST", "{\"name\":\"gadget\"}", "{\"id\": 1, \"status\":\"201 Created\"}");
        crudFunctions.insert("http://test.com/api/orders", "GET", "", "{\"status\":\"200 OK\"}");
        System.out.println("Inserted 3 sample documents.");

        // 2. FIND ALL: Show all documents in the collection
        System.out.println("\n--- 2. Testing FIND ALL ---");
        crudFunctions.findAll();

        // 3. FIND ONE: Show the first document
        System.out.println("\n--- 3. Testing FIND ONE ---");
        crudFunctions.findOne();

        // 4. FIND BY REQUEST_ID and FIND BY _id
        System.out.println("\n--- 4. Testing FIND BY ID ---");
        // Find the document we want to test with (e.g., the one with request_id = 1)
        Document docToTest = crudFunctions.findByRequestId(1);

        if (docToTest != null) {
            // Get its MongoDB _id
            ObjectId objectId = docToTest.getObjectId("_id");
            String idString = objectId.toHexString();

            // Now test findById with the string version of the _id
            crudFunctions.findById(idString);

            // 5. DELETE BY ID: Delete the document we just found
            System.out.println("\n--- 5. Testing DELETE BY ID ---");
            crudFunctions.deleteById(idString);

            // 6. VERIFY DELETION: Call findAll again to show the document is gone
            System.out.println("\n--- 6. Verifying Deletion (should be missing request_id: 1) ---");
            crudFunctions.findAll();
        }

        System.out.println("\n=== Test Suite Finished ===");
    }
}
