# Phase 1: REST API Tester CLI

This is the original, lightweight version of the REST API Tester. It's a single Java file with no external dependencies, perfect for quick, simple API testing directly in your terminal.

---

##  Features

- Enter any API **URL** and choose **HTTP method** (`GET`, `POST`, `PUT`, `DELETE`).
- Supports **JSON payload input** for `POST` and `PUT`.
- Prints **HTTP status code** and **response body**.

---

##  Setup & Run

1.  **Navigate to this directory (`Phase_1`)**.
2.  **Compile the Java program**:

```bash
javac RestApiTester.java
```

### 3. Run the program

```bash
java RestApiTester
```

---

##  Usage Example

```text
=== Simple REST API Tester ===

Enter URL (or type 'exit' to quit): https://jsonplaceholder.typicode.com/posts/1
Enter HTTP Method (GET/POST/PUT/DELETE): GET

--- Response ---
Status: 200
Body:
{
  "userId": 1,
  "id": 1,
  "title": "sunt aut facere repellat provident occaecati",
  "body": "quia et suscipit..."
}

===============================
```

Example with **POST** request:

```text
Enter URL (or type 'exit' to quit): https://jsonplaceholder.typicode.com/posts
Enter HTTP Method (GET/POST/PUT/DELETE): POST
Enter JSON payload (end with a single line 'END'):
{
  "title": "foo",
  "body": "bar",
  "userId": 1
}
END

--- Response ---
Status: 201
Body:
{
  "id": 101,
  "title": "foo",
  "body": "bar",
  "userId": 1
}
```

---

##  Project Structure

```
.
├── RestApiTester.java   # Main program
└── README.md            # Documentation
```

---


