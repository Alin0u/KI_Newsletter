package kgn.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void sendOneMail(String to, String subject, String text) throws MessagingException {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);

            javaMailSender.send(message);
    }

    /**
     * Sends emails to a list of recipients with the specified subject and text content.
     *
     * @param toList  The list of email addresses of the recipients.
     * @param subject The subject of the email.
     * @param text    The main content of the email.
     * @throws MessagingException If an error occurs during the messaging process.
     */
    public void sendMails(List<String> toList, String subject, String text) throws MessagingException {
        for (String to : toList) {
            sendOneMail(to, subject, text);
        }
    }

}
