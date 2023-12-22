package kgn.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;


/**
 * Configuration class for mail services.
 * This class configures the JavaMailSender to be used for sending emails.
 */
@Configuration
public class MailConfig {

    /**
     * Creates a {@link JavaMailSender} bean with properties configured from {@link MailProperties}.
     * This method sets up the JavaMailSender with SMTP properties, including host, port, username, and password.
     * It is marked as {@link Lazy} to indicate that the bean should be created and initialized only as necessary.
     *
     * @param mailProperties The mail properties to configure the JavaMailSender.
     * @return A configured instance of {@link JavaMailSender}.
     */
    @Lazy
    @Bean
    public JavaMailSender javaMailSender(MailProperties mailProperties) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(mailProperties.getMail());
        mailSender.setPort(mailProperties.getMailport());
        mailSender.setUsername(mailProperties.getMail());
        mailSender.setPassword(mailProperties.getMailpassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}

