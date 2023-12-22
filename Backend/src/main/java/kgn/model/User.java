package kgn.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a user entity in the application.
 * This class is mapped to the "users" table in the database and includes fields for the user's
 * ID, username, password, and role.
 */
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String role;
    private String mail;
    private String mailserver;
    private String mailpassword;
    private Integer mailport;
    private String openaikey;
    private String sendgridkey;

    /**
     * Constructs a new User with specified username, password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Default constructor for the User entity.
     */
    public User() {
    }

    /**
     * Constructs a new User with the specified details.
     *
     * @param id            The unique identifier of the user.
     * @param username      The username of the user.
     * @param password      The password of the user.
     * @param role          The role assigned to the user.
     * @param mail          The email address associated with the user.
     * @param mailserver    The address of the mail server used by the user.
     * @param mailpassword  The password for the user's email.
     * @param mailport      The port number of the mail server.
     * @param openaikey     The OpenAI API key associated with the user.
     * @param sendgridkey   The SendGrid API key associated with the user.
     */
    public User(Integer id, String username, String password, String role, String mailserver, String mailpassword,
                int mailport, String openaikey, String sendgridkey, String mail) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.mail = mail;
        this.mailserver = mailserver;
        this.mailpassword = mailpassword;
        this.mailport = mailport;
        this.openaikey = openaikey;
        this.sendgridkey = sendgridkey;
    }
}
