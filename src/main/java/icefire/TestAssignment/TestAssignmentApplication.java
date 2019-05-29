package icefire.TestAssignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestAssignmentApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TestAssignmentApplication.class, args);
	}
	
	/*@Bean
	InitializingBean initDB() {
		return () -> {
			repository.save(new Encrypted(Base64.getEncoder().encodeToString("Hello World!".getBytes())));
		};
	}*/

}
