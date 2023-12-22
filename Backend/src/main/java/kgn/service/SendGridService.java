package kgn.service;

import com.sendgrid.helpers.mail.objects.Personalization;
import kgn.controller.ContactListController;
import kgn.model.User;
import kgn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import java.io.IOException;
import java.util.List;

@Service
public class SendGridService {
    @Autowired
    UserRepository userRepository;

    private SendGrid sendGrid;
    private String fromEmail;

    public SendGridService() {}

    /**
     * Sends the specified email using the SendGrid API.
     *
     * @param mail The email message to be sent.
     */
    private void sendEmailToSendGrid(Mail mail) {
        try {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);
            int statusCode = response.getStatusCode();
            if (statusCode < 200 || statusCode >= 300) {
                throw new RuntimeException(response.getBody());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Sends bulk emails to multiple recipients.
     *
     * @param tos     The list of email addresses to send the emails to.
     * @param subject The subject of the email.
     * @param text    The HTML content of the email.
     */
    public void sendBulkEmails(List<String> tos, String subject, String text) {
        initializeSendGrid();

        Mail mail = new Mail();
        mail.setFrom(new Email(this.fromEmail));
        mail.setSubject(subject);
        mail.addContent(new Content("text/html", text));
        Personalization personalization = new Personalization();
        personalization.addTo(new Email(this.fromEmail));
        tos.forEach(to -> {
            personalization.addBcc(new Email(to));

        });
        mail.addPersonalization(personalization);

        sendEmailToSendGrid(mail);
    }

    /**
     * Initializes the SendGrid client using the current user's settings.
     *
     * @throws IllegalStateException if the current username is not available in the security context.
     * @throws UsernameNotFoundException if the user with the current username is not found in the database.
     */
    private void initializeSendGrid() {
        String currentUsername = ContactListController.getCurrentUsername();
        if (currentUsername == null || currentUsername.isEmpty()) {
            throw new IllegalStateException("Current username is not available.");
        }
        fromEmail = userRepository.findByUsername(currentUsername).get().getMail();
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + currentUsername));

        this.sendGrid = new SendGrid(user.getSendgridkey());
    }

    /**
     * Updates the SendGrid client with a new API key.
     *
     * @param apiKey The new SendGrid API key to use.
     */
    public void updateSendGridApiKey(String apiKey) {
        this.sendGrid = new SendGrid(apiKey);
    }

}
