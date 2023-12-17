package kgn;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MailRequest {
    private List<String> tos;
    private String subject;
    private String text;


    public MailRequest(List<String> tos, String subject, String text) {
        this.tos = tos;
        this.subject = subject;
        this.text = text;
    }

}