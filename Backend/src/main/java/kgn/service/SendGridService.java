package kgn.service;

import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final SendGrid sendGrid;
    private final String fromEmail;



    public SendGridService(
            // get the SendGrid bean automatically created by Spring Boot
            @Autowired SendGrid sendGrid,
            // read your email to use as sender from application.properties
            @Value("${twilio.sendgrid.from-email}") String fromEmail
    ) {
        this.sendGrid = sendGrid;
        this.fromEmail = fromEmail;
    }

    private void sendEmailToSendGrid(Mail mail) {
        try {
            // set the SendGrid API endpoint details as described
            // in the doc (https://docs.sendgrid.com/api-reference/mail-send/mail-send)
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            // perform the request and send the email
            Response response = sendGrid.api(request);
            int statusCode = response.getStatusCode();
            // if the status code is not 2xx
            if (statusCode < 200 || statusCode >= 300) {
                throw new RuntimeException(response.getBody());
            }
        } catch (IOException e) {
            // log the error message in case of network failures
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public void sendBulkEmails(List<String> tos, String subject, String text) {
        // specify the email details
        Mail mail = new Mail();
        mail.setFrom(new Email(this.fromEmail));
        mail.setSubject(subject);
        mail.addContent(new Content("text/html", text));
        // add the multiple recipients to the email
        Personalization personalization = new Personalization();
        personalization.addTo(new Email(this.fromEmail));
        tos.forEach(to -> {
            personalization.addBcc(new Email(to));

        });
        mail.addPersonalization(personalization);

        // send the bulk email
        sendEmailToSendGrid(mail);

    }

}
