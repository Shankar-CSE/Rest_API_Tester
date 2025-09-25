
# Phase 2 Database Connectivity Guide

## MongoDB Connection

### Prerequisites
- MongoDB installed and running
- Database and collection created
- MongoDB Compass (optional) for GUI management
- Network access configured (if using MongoDB Atlas)
- Authentication credentials ready (username/password)

### Basic ConnectionMongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
MongoDatabase database = mongoClient.getDatabase("databaseName");
MongoCollection<Document> collection = database.getCollection("collectionName");

## Flow driver creation

Drivers are merged in to single driver => App-all.jar.

App.java file access the driver from the lib forlder 
(driver folder contants only old drivers ehich are all now necessary)

build-fatjar.bat file will create the App-all.jar file from old drivers which is enought to connect MongoDb with java project


## Run Command

(move to the Phase_2 directory)
then run this command 
```
./run.bat
```