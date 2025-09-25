
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

### Connection with AuthenticationMongoCredential credential = MongoCredential.
