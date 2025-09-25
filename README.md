# REST API Tester

This project contains two versions of a REST API testing tool: a simple command-line interface (CLI) and an enhanced version with MongoDB integration to save request history.

---

## Phases

This project is divided into two phases:

*   **Phase 1:** A lightweight, dependency-free CLI for making simple HTTP requests.
*   **Phase 2:** An enhanced version that stores request and response data in a MongoDB database.

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
└── README.md              # This file
```