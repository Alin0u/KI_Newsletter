package kgn.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Service class for handling email-related functionality using JavaMailSender.
 */
@Service
public class MailService {
    private final JavaMailSender javaMailSender;

    /**
     * Constructor to inject the JavaMailSender dependency.
     *
     * @param javaMailSender The JavaMailSender used to send emails.
     */
    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Sends an email with the specified details.
     *
     * @param to      The email address of the recipient.
     * @param subject The subject of the email.
     * @param text    The main content of the email.
     * @throws MessagingException If an error occurs during the messaging process.
     */
    public void sendMail(String to, String subject, String text) throws MessagingException {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);

            javaMailSender.send(message);

    }

}
