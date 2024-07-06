package com.example.reactor_project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Component
public class MongoDBConnectionTest implements CommandLineRunner {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public void run(String... args) throws Exception {
        reactiveMongoTemplate.executeCommand("{ ping: 1 }")
                .doOnSuccess(result -> System.out.println("MongoDB connection is OK: " + result))
                .doOnError(error -> System.err.println("MongoDB connection failed: " + error.getMessage()))
                .subscribe();
    }
}