package org.mjjaenl.reactivetutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class ReactivetutorialApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReactivetutorialApplication.class, args);
	}
}
