package kgn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"kgn"})
@SpringBootApplication
public class KiGestaerkteNewsletterApplication {
	public static void main(String[] args) {
		SpringApplication.run(KiGestaerkteNewsletterApplication.class, args);
	}

}
