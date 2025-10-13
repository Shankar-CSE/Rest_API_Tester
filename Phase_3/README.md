## Phase 3: GUI and Enhanced CLI

This is the final and most advanced version of the REST API Tester. It offers both a graphical user interface (GUI) built with JavaFX and an improved command-line interface (CLI), both of which interact with a MongoDB database.

### Features

-   **JavaFX GUI**: A user-friendly interface with two tabs:
    -   **API Tester**: Make `GET`, `POST`, `PUT`, and `DELETE` requests, with support for JSON payloads.
    -   **History**: View all past requests, see the last request, or find a specific request by its ID.
-   **Enhanced CLI**: An improved command-line interface with more options for interacting with the database history.
-   **MongoDB Integration**: All requests and responses from both the GUI and CLI are saved to a MongoDB database.
-   **Auto-Incrementing IDs**: Each request is assigned a unique, auto-incrementing `request_id`.

### Prerequisites

-   **Java Development Kit (JDK)**
-   **JavaFX SDK**: The project is configured to use JavaFX.
-   **MongoDB**: A local instance must be running at `mongodb://localhost:27017`.

### Build & Run

#### GUI Application

1.  **Navigate to the `Phase_3` directory**.
2.  **Execute the `runUi.bat` script**:

    ```bash
    ./runUi.bat
    ```

    This script compiles and runs the JavaFX application.

#### CLI Application

1.  **Navigate to the `Phase_3` directory**.
2.  **Execute the `run.bat` script**:

    ```bash
    ./run.bat
    ```

    This script compiles and runs the CLI application.

#### Pre-compiled JAR

A pre-compiled JAR file is also available in the `Phase_3` directory. You can run it directly using the `run-jar.bat` script:

```bash
./run-jar.bat
```

---