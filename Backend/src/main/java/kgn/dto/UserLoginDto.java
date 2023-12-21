package kgn.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO used for transferring user login information.
 */
@Getter
@Setter
public class UserLoginDto {
    private String username;
    private String password;
    private String role;

    public UserLoginDto() {
    }

    public UserLoginDto(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
