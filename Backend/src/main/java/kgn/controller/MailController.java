package kgn.controller;


import jakarta.mail.MessagingException;
import kgn.MailRequest;
import kgn.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    /**
     * Constructor to inject the MailService dependency.
     *
     * @param mailService The MailService used to send emails.
     */
    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * Endpoint for sending emails.
     *
     * @param mailRequest The {@link MailRequest} object containing email details.
     * @return A {@link ResponseEntity} with a JSON string indicating the success or failure
     *         of the email sending process. In case of success, the JSON contains a "message"
     *         property with the success message. In case of an error, the JSON contains an "error"
     *         property with the error message.
     */
    @PostMapping("/send")
    public ResponseEntity<String> sendMail(@RequestBody MailRequest mailRequest) {
        try {
            mailService.sendMail(mailRequest.getTo(), mailRequest.getSubject(), mailRequest.getText());
            return ResponseEntity.ok("{\"message\": \"E-Mail erfolgreich gesendet!\"}");
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Fehler beim Senden der E-Mail.\"}");
        }
    }
}
