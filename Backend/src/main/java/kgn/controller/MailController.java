package kgn.controller;


import jakarta.mail.MessagingException;
import kgn.MailRequest;
import kgn.model.User;
import kgn.repository.UserRepository;
import kgn.service.MailService;
import kgn.service.SendGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    private MailService mailService;
    private SendGridService sendGridService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Constructor to inject the MailService dependency.
     *
     * @param mailService The MailService used to send emails.
     */
    public MailController(MailService mailService, SendGridService sendGridService) {
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
        String currentUsername = ContactListController.getCurrentUsername();
        if (currentUsername == null || currentUsername.isEmpty()) {
            throw new IllegalStateException("Current username is not available.");
        }

        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + currentUsername));

        String sendGridApiKey = user.getSendgridkey();

        if (sendGridApiKey != null && !sendGridApiKey.isEmpty()) {
            sendGridService.updateSendGridApiKey(sendGridApiKey);
            sendGridService.sendBulkEmails(mailRequest.getTos(), mailRequest.getSubject(), mailRequest.getText());
            return ResponseEntity.ok("{\"message\": \"E-Mail successfully sent via SendGrid!\"}");
        } else {
            try {
                mailService.sendMails(mailRequest.getTos(), mailRequest.getSubject(), mailRequest.getText());
                return ResponseEntity.ok("{\"message\": \"E-Mail successfully sent!\"}");
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
