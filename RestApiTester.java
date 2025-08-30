import java.io.*;
import java.net.*;
import java.util.*;

// Main class to run the REST API Tester
public class RestApiTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Simple REST API Tester ===");

        while (true) {
            System.out.print("\nEnter URL (or type 'exit' to quit): ");
            String urlStr = scanner.nextLine();
            if (urlStr.equalsIgnoreCase("exit")) break;

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
            System.out.println("Body:\n" + response.getBody());
            System.out.println("\n===============================");
        }

        System.out.println("Exiting REST API Tester. Goodbye!");
        scanner.close();
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
        } catch (Exception e) {
            response.append("Error: ").append(e.getMessage());
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
