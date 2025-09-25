// package Phase_1;
import java.io.*;
import java.net.*;
import java.util.Scanner;

// Import CRUDfunctions from the same package
import org.bson.Document;

// Main class to run the REST API Tester
public class App {

    private static Scanner scanner = new Scanner(System.in);
    private static CRUDfunctions crudFunctions = new CRUDfunctions(); // Initialize CRUDfunctions once

    public static void main(String[] args) {
        System.out.println("=== REST API Tester with MongoDB History ===");
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Run REST API Tester");
            System.out.println("2. Database History");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    runRestApiTester();
                    break;
                case "2":
                    runDatabaseHistory();
                    break;
                case "3":
                    System.out.println("Exiting application. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    public static void runRestApiTester() {
     
        while (true) {
            System.out.print("\nEnter URL (or type 'back' to go back or 'exit' to quit): ");
            String urlStr = scanner.nextLine();
            if (urlStr.equalsIgnoreCase("back")) break;
            if (urlStr.equalsIgnoreCase("exit")) return;

            System.out.print("Enter HTTP Method (GET/POST/PUT/DELETE): ");
            String method = scanner.nextLine().toUpperCase();

            String payload = null;
            if (method.equals("POST") || method.equals("PUT")) {
                System.out.println("Enter JSON payload (end with a single line 'END'):");
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String line = scanner.nextLine();
                    if (line.equals("END")) break;
                    sb.append(line).append("\n");
                }
                payload = sb.toString().trim();
                if (payload.isEmpty()) payload = null;
            }

            // Create a request object and send it
            ApiRequest request = new ApiRequest(urlStr, method, payload);
            ApiResponse response = request.send();

            // Display the response
            System.out.println("\n--- Response ---");
            System.out.println("Status: " + response.getStatus());
            System.out.println("Body:\n" + response.getBody().trim()); // Trim to avoid extra newlines
            System.out.println("\n===============================");

            // Save the request and response to MongoDB
            try {
                String fullResponseString = "Status: " + response.getStatus() + "\nBody:\n" + response.getBody().trim();
                crudFunctions.insert(urlStr, method, payload != null ? payload : "", fullResponseString);
                System.out.println("API request and response saved to database with request_id: " + crudFunctions.getLastRequestId());
            } catch (Exception e) {
                System.err.println("Error saving to database: " + e.getMessage());
            }
        }
        System.out.println("\nReturning to Main Menu.");
    }


    public static void runDatabaseHistory() {
        System.out.println("\n--- Database History ---");
        while (true) {
            System.out.println("\nDatabase Operations:");
            System.out.println("1. Find All Requests");
            System.out.println("2. Find First Request");
            System.out.println("3. Find Request by Custom ID (request_id)");
            System.out.println("4. Find Request by MongoDB _id");
            System.out.println("5. Delete Request by MongoDB _id");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    crudFunctions.findAll();
                    break;
                case "2":
                    crudFunctions.findOne();
                    break;
                case "3":
                    System.out.print("Enter custom request_id to find: ");
                    try {
                        long requestId = Long.parseLong(scanner.nextLine());
                        crudFunctions.findByRequestId(requestId);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number for request_id.");
                    }
                    break;
                case "4":
                    System.out.print("Enter MongoDB _id to find: ");
                    String mongoIdToFind = scanner.nextLine();
                    crudFunctions.findById(mongoIdToFind);
                    break;
                case "5":
                    System.out.print("Enter MongoDB _id to delete: ");
                    String mongoIdToDelete = scanner.nextLine();
                    crudFunctions.deleteById(mongoIdToDelete);
                    break;
                case "6":
                    System.out.println("Returning to Main Menu.");
                    return; // Exit the database history loop
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}



// Represents an API request
class ApiRequest {
    private String url;
    private String method;
    private String payload;

    public ApiRequest(String url, String method, String payload) {
        this.url = url;
        this.method = method;
        this.payload = payload;
    }

    // Sends the HTTP request and returns the response
    public ApiResponse send() {
        int status = -1;
        StringBuilder response = new StringBuilder();

        try {
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", "application/json");

            // Write payload if POST/PUT
            if (payload != null) {
                conn.setDoOutput(true);
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(payload.getBytes("UTF-8"));
                }
            }

            // Get response code and stream
            status = conn.getResponseCode();
            InputStream is = (status >= 200 && status < 300)
                    ? conn.getInputStream()
                    : conn.getErrorStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line).append("\n");
            }
            in.close();
        } catch (java.net.UnknownHostException e) {
            response.append("Network Error: Could not resolve host '").append(e.getMessage()).append("'.\nPlease check your internet connection and the URL.");
        } catch (Exception e) {
            response.append("An unexpected error occurred: ").append(e.getClass().getSimpleName()).append(" - ").append(e.getMessage());
        }

        return new ApiResponse(status, response.toString());
    }
}

// Represents an API response
class ApiResponse {
    private int status;
    private String body;

    public ApiResponse(int status, String body) {
        this.status = status;
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }
}


// Sample url for testing 

// https://api.restful-api.dev/objects
// https://jsonplaceholder.typicode.com/todos