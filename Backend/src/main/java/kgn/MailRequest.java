package kgn;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MailRequest {
    private String to;
    private String subject;
    private String text;

    public MailRequest(String to, String subject, String text) {
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

}