package kgn.service;

import kgn.model.User;
import kgn.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Component responsible for storing and managing mail properties.
 * This class holds properties related to mail configuration and provides
 * a method to load these properties from the database for a specific user.
 */
@Getter
@Setter
@Component
public class MailProperties {
    @Autowired
    UserRepository userRepository;

    private String mail;
    private String mailserver;
    private String mailpassword;
    private int mailport;
    private String sendgridkey;

    /**
     * Loads mail configuration properties from the database for a given username.
     * This method retrieves the user's mail settings from the database and populates
     * the properties of this component.
     *
     * @param username The username for which to load mail properties.
     */
    public void loadFromDatabase(String username) {
        User user = userRepository.findByUsername(username).get();
        this.mail = user.getMail();
        this.mailserver = user.getMailserver();
        this.mailpassword = user.getMailpassword();
        this.mailport = user.getMailport();
        this.sendgridkey = user.getSendgridkey();
    }
}
