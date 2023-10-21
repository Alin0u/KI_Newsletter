package KGN.KIgestaerkteNewsletter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication //(exclude = {SecurityAutoConfiguration.class }) for testing
public class KiGestaerkteNewsletterApplication {

	public static void main(String[] args) {
		SpringApplication.run(KiGestaerkteNewsletterApplication.class, args);
	}

}
