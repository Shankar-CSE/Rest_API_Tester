# Phase 2: REST API Tester with MongoDB

This guide explains how to set up and run the enhanced version of the REST API Tester, which includes database connectivity to store request history.

---

## 1. Prerequisites

- **Java Development Kit (JDK)**: Ensure you have a JDK installed and configured.
- **MongoDB**: A local MongoDB instance must be running. The application connects to `mongodb://localhost:27017` by default.
- **MongoDB Compass (Optional)**: A useful GUI tool for viewing the data stored in the `RestAPI_Tester` database and `Requests` collection.

The application will automatically create the `RestAPI_Tester` database and the `Requests` collection on its first run.

---

## 2. Build & Run

The project includes scripts to simplify compilation and execution.

### To Run the Application

Navigate to the `Phase_2` directory in your terminal and execute the `run.bat` script. This script handles compiling all necessary `.java` files and running the application.

```bash
# Make sure you are in the Phase_2 directory
./run.bat
```

### To Build a Distributable JAR (Optional)

If you want to create a single, self-contained "fat JAR" that includes all dependencies, you can run the `build-fatjar.bat` script.

```
./run.bat
```
