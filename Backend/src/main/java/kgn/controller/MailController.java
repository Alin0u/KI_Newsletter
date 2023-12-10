package kgn.controller;


import jakarta.mail.MessagingException;
import kgn.MailRequest;
import kgn.service.MailService;
import kgn.service.SendGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import kgn.service.SendGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Controller class for handling email-related requests.
 */
@RestController
@RequestMapping("/api/mail")
public class MailController {

    private final MailService mailService;
    private final SendGridService sendGridService;

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


    @PostMapping("/send")
    public ResponseEntity<String> sendMail(@RequestBody MailRequest mailRequest) {
        List<String> tosList = mailRequest.getTos();
        if(tosList != null && tosList.size() == 1){
            try {
                mailService.sendMail(tosList.get(0), mailRequest.getSubject(), mailRequest.getText());
                return ResponseEntity.ok("{\"message\": \"E-Mail erfolgreich gesendet!\"}");
            } catch (MessagingException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Fehler beim Senden der E-Mail.\"}");
            }
        }else{
            sendGridService.sendBulkEmails(mailRequest.getTos(), mailRequest.getSubject(), mailRequest.getText());

            return ResponseEntity.ok("Bulk emails sent successfully!");
        }

    }
}
