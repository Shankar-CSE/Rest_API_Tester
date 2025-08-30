import java.util.*;

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

            System.out.println("\n--- Request Info ---");
            System.out.println("URL: " + urlStr);
            System.out.println("Method: " + method);
            if (payload != null) {
                System.out.println("Payload:\n" + payload);
            }
            System.out.println("\n===============================");
        }

        System.out.println("Exiting REST API Tester. Goodbye!");
        scanner.close();
    }
}
