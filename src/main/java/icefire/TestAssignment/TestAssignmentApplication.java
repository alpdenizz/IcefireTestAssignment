package icefire.TestAssignment;


import java.util.Base64;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import icefire.TestAssignment.domain.Encrypted;
import icefire.TestAssignment.repository.EncryptedRepository;

@SpringBootApplication
public class TestAssignmentApplication {
	
	@Autowired
	private EncryptedRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(TestAssignmentApplication.class, args);
	}
	
	@Bean
	InitializingBean initDB() {
		return () -> {
			repository.save(new Encrypted(Base64.getEncoder().encodeToString("Hello World!".getBytes())));
		};
	}

}
