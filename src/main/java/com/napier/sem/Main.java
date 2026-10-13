package com.napier.sem;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Main {

    public static void main(String[] args) {

        System.out.println("Hello and welcome!");

        String connectionString = System.getenv("MONGO_URI");

        if (connectionString == null || connectionString.isBlank()) {
            connectionString = "mongodb://localhost:27000";
        }

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase database = mongoClient.getDatabase("test");

            Document result = database.runCommand(new Document("ping", 1));

            System.out.println("Successfully connected to MongoDB!");
            System.out.println("MongoDB response: " + result.toJson());

        } catch (Exception e) {
            System.err.println("MongoDB connection failed: " + e.getMessage());
        }
    }
}