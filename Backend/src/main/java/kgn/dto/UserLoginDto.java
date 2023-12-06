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

    public UserLoginDto() {
    }
}
