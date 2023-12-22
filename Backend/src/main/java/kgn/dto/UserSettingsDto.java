package kgn.dto;

import lombok.*;

/**
 * Data Transfer Object for user settings.
 * This class is used to transfer user settings data between different layers
 * of the application, typically from the presentation layer to the service layer.
 */
@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSettingsDto {
    private String mail;
    private String mailserver;
    private String mailpassword;
    private Integer mailport;
    private String openaikey;
    private String sendgridkey;
}
