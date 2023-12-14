package kgn.controller;


import jakarta.mail.MessagingException;
import kgn.MailRequest;
import kgn.service.MailService;
import kgn.service.SendGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller class for handling email-related requests.
 */
@RestController
@RequestMapping("/api/mail")
public class MailController {

    private final MailService mailService;
    private final SendGridService sendGridService;

    @Value("${spring.sendgrid.api-key}")
    private String sendGridApiKey;

    /**
     * Constructor to inject the MailService dependency.
     *
     * @param mailService The MailService used to send emails.
     */
    @Autowired
    public MailController(
            MailService mailService,
            @Autowired SendGridService sendGridService
    ) {
        this.mailService = mailService;
        this.sendGridService = sendGridService;
    }

    /**
     * Handles the endpoint for sending emails based on the provided MailRequest.
     *
     * @param mailRequest The MailRequest containing email details such as recipients, subject, and text.
     * @return ResponseEntity with a success message if the email is sent successfully.
     */
    @PostMapping("/send")
    public ResponseEntity<String> sendMail(@RequestBody MailRequest mailRequest) {
        if(isSendGridApiKeySet()){
            sendGridService.sendBulkEmails(mailRequest.getTos(), mailRequest.getSubject(), mailRequest.getText());
            return ResponseEntity.ok("{\"message\": \"E-Mail erfolgreich über sendGrid gesendet!\"}");
        }else{
            try {
                mailService.sendMails(mailRequest.getTos(), mailRequest.getSubject(), mailRequest.getText());
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            return ResponseEntity.ok("{\"message\": \"E-Mail erfolgreich gesendet!\"}");
        }
    }

    /**
     * Checks if the SendGrid API key is set.
     *
     * @return {@code true} if the SendGrid API key is set and not empty, {@code false} otherwise.
     */
    private boolean isSendGridApiKeySet() {
        return sendGridApiKey != null && !sendGridApiKey.isEmpty();
    }
}
