# REST API Tester

This project contains three versions of a REST API testing tool: a simple command-line interface (CLI), an enhanced version with MongoDB integration to save request history, and a final version with a JavaFX GUI.

---

## Phases

This project is divided into three phases:

*   **Phase 1:** A lightweight, dependency-free CLI for making simple HTTP requests.
*   **Phase 2:** An enhanced version that stores request and response data in a MongoDB database.
*   **Phase 3:** The final version, which includes a JavaFX GUI, a CLI with database history, and improved database functionality.

---

## Phase 1: Simple CLI Tool

This is the original, lightweight version of the REST API Tester. It's a single Java file with no external dependencies, perfect for quick, simple API testing directly in your terminal.

### Features

-   Enter any API **URL** and choose **HTTP method** (`GET`, `POST`, `PUT`, `DELETE`).
-   Supports **JSON payload input** for `POST` and `PUT`.
-   Prints **HTTP status code** and **response body**.

### Setup & Run

1.  **Navigate to the `Phase_1` directory**.
2.  **Compile and run the Java program**:

    ```bash
    javac RestApiTester.java
    java RestApiTester
    ```

---

## Phase 2: Enhanced Tool with MongoDB

This version expands on the original by adding a MongoDB backend to persist request history.

### Features

-   All features from Phase 1.
-   Saves each request's URL, method, payload, and the response (status and body) to a MongoDB database.
-   Automatically creates the `RestAPI_Tester` database and `Requests` collection.

### Prerequisites

-   **Java Development Kit (JDK)**
-   **MongoDB**: A local instance must be running at `mongodb://localhost:27017`.

### Build & Run

1.  **Navigate to the `Phase_2` directory**.
2.  **Execute the `run.bat` script**:

    ```bash
    ./run.bat
    ```

    This script compiles all source files and runs the application.

---

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

## Project Structure

```
.
├── Phase_1/               # Simple, dependency-free CLI tool
│   ├── RestApiTester.java
│   └── README.md
├── Phase_2/               # Enhanced tool with MongoDB integration
│   ├── src/
│   ├── lib/
│   ├── run.bat
│   └── README.md
├── Phase_3/               # Final version with GUI and enhanced CLI
│   ├── src/
│   ├── lib/
│   ├── run.bat
│   ├── runUi.bat
│   ├── run-jar.bat
│   └── Phase_3.jar
└── README.md              # This file
```
