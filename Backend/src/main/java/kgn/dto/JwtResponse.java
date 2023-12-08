package kgn.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO that encapsulates the JWT used in authentication responses.
 */
public class JwtResponse {

    @Setter
    @Getter
    private String token;


    /**
     * Constructs a new JwtResponse with the specified JWT token.
     *
     * @param token the JWT token to be encapsulated in the response.
     */
    public JwtResponse(String token) {
        this.token = token;
    }
}
