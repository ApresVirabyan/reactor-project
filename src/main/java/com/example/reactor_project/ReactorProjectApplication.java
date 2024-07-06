package com.example.reactor_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class ReactorProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactorProjectApplication.class, args);
	}

}
