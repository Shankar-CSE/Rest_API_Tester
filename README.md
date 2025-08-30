


# REST API Tester (Java CLI)

A lightweight **command-line REST API tester** written in Java.  
Think of it as a mini **Postman/Insomnia**, but directly in your terminal. 🚀  

---

## ✨ Features
- Enter any API **URL** and choose **HTTP method** (`GET`, `POST`, `PUT`, `DELETE`).
- Supports **JSON payload input** for `POST` and `PUT`.
- Prints **HTTP status code** and **response body**.
- Handles **error messages** if the request fails.
- Runs in a simple loop until you type `exit`.

---

## 📦 Setup & Run

### 1. Clone the repository
```bash
git clone https://github.com/Shankar-CSE/Rest_API_Tester.git
cd Rest_API_Tester
````

### 2. Compile the Java program

```bash
javac RestApiTester.java
```

### 3. Run the program

```bash
java RestApiTester
```

---

## 🖥️ Usage Example

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

## 📂 Project Structure

```
.
├── RestApiTester.java   # Main program
└── README.md            # Documentation
```

---


Happy testing! 🎉


