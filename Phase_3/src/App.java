import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.io.*;
import java.net.*;

public class App extends Application {
    private CRUDfunctionsUI crudFunctions = new CRUDfunctionsUI();
    
    @Override
    public void start(Stage stage) {
        TabPane tabPane = new TabPane();
        
        // API Tester Tab
        Tab apiTab = new Tab("API Tester");
        apiTab.setClosable(false);
        apiTab.setContent(createApiTesterPane());
        
        // History Tab
        Tab historyTab = new Tab("History");
        historyTab.setClosable(false);
        historyTab.setContent(createHistoryPane());
        
        tabPane.getTabs().addAll(apiTab, historyTab);
        
        Scene scene = new Scene(tabPane, 800, 600);
        stage.setTitle("REST API Tester with MongoDB");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
    
    private VBox createApiTesterPane() {
        TextField urlField = new TextField("https://api.restful-api.dev/objects/1");
        ComboBox<String> methodBox = new ComboBox<>();
        methodBox.getItems().addAll("GET", "POST", "PUT", "DELETE");
        methodBox.setValue("GET");
        
        TextArea payloadArea = new TextArea();
        payloadArea.setPrefRowCount(5);
        payloadArea.setPromptText("Enter JSON payload for POST/PUT requests");
        
        Button sendButton = new Button("Send Request");
        TextArea responseArea = new TextArea();
        responseArea.setPrefRowCount(15);
        responseArea.setEditable(false);
        
        sendButton.setOnAction(e -> {
            String url = urlField.getText();
            String method = methodBox.getValue();
            String payload = payloadArea.getText().trim();
            if (payload.isEmpty()) payload = null;
            
            ApiRequest request = new ApiRequest(url, method, payload);
            ApiResponse response = request.send();
            
            String responseText = "Status: " + response.getStatus() + "\n\n" + response.getBody();
            responseArea.setText(responseText);
            
            try {
                crudFunctions.insert(url, method, payload != null ? payload : "", responseText);
                responseArea.appendText("\n\n[Saved to database with ID: " + crudFunctions.getLastRequestId() + "]");
            } catch (Exception ex) {
                responseArea.appendText("\n\n[Database error: " + ex.getMessage() + "]");
            }
            
            payloadArea.clear();
        });
        
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(
            new Label("URL:"), urlField,
            new Label("Method:"), methodBox,
            new Label("Payload:"), payloadArea,
            sendButton,
            new Label("Response:"), responseArea
        );
        
        return root;
    }
    
    private VBox createHistoryPane() {
        TextArea historyArea = new TextArea();
        historyArea.setPrefRowCount(20);
        historyArea.setEditable(false);
        
        TextField requestIdField = new TextField();
        requestIdField.setPromptText("Enter request ID");
        
        Button findAllButton = new Button("Show All");
        Button findLastButton = new Button("Show Last");
        Button findByIdButton = new Button("Find by ID");
        Button clearButton = new Button("Clear");
        
        findAllButton.setOnAction(e -> {
            historyArea.clear();
            historyArea.appendText("=== All Requests ===\n\n");
            historyArea.appendText(crudFunctions.findAll());
        });
        
        findLastButton.setOnAction(e -> {
            historyArea.clear();
            historyArea.appendText("=== Last Request ===\n\n");
            historyArea.appendText(crudFunctions.findLastInserted());
        });
        
        findByIdButton.setOnAction(e -> {
            try {
                long id = Long.parseLong(requestIdField.getText());
                historyArea.clear();
                historyArea.appendText("=== Request ID: " + id + " ===\n\n");
                historyArea.appendText(crudFunctions.findByRequestId(id));
            } catch (NumberFormatException ex) {
                historyArea.setText("Invalid ID format");
            }
        });
        
        clearButton.setOnAction(e -> historyArea.clear());
        
        HBox buttonBox = new HBox(10, findAllButton, findLastButton, findByIdButton, clearButton);
        HBox idBox = new HBox(10, new Label("Request ID:"), requestIdField, findByIdButton);
        
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(buttonBox, idBox, new Label("History:"), historyArea);
        
        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class ApiRequest {
    private String url;
    private String method;
    private String payload;

    public ApiRequest(String url, String method, String payload) {
        this.url = url;
        this.method = method;
        this.payload = payload;
    }

    public ApiResponse send() {
        int status = -1;
        StringBuilder response = new StringBuilder();

        try {
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", "application/json");

            if (payload != null) {
                conn.setDoOutput(true);
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(payload.getBytes("UTF-8"));
                }
            }

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