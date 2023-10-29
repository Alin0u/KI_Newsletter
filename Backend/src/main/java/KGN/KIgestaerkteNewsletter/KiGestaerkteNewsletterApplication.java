package KGN.KIgestaerkteNewsletter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"KGN.KIgestaerkteNewsletter"})
@SpringBootApplication //(exclude = {SecurityAutoConfiguration.class }) // activate for testing only
public class KiGestaerkteNewsletterApplication {
	public static void main(String[] args) {
		SpringApplication.run(KiGestaerkteNewsletterApplication.class, args);
	}

}
