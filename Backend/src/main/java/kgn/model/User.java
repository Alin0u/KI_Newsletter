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
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Integer id;
    private String username;
    private String password;
    private String role;

    /**
     * Constructs a new User with specified username, password, and role.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @param role     The role of the user.
     */
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Default constructor for the User entity.
     */
    public User() {
    }
}
