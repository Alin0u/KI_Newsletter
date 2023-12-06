package kgn.controller;

import kgn.dto.JwtResponse;
import kgn.dto.UserLoginDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

/**
 * REST controller responsible for handling authentication requests.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtEncoder encoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Constructor for AuthController.
     *
     * @param encoder                An instance of {@link JwtEncoder} used for JWT token encoding.
     * @param authenticationManager  An instance of {@link AuthenticationManager} used for user authentication.
     */
    public AuthController(JwtEncoder encoder, AuthenticationManager authenticationManager) {
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Authenticates a user based on username and password, and generates a JWT token.
     * This endpoint accepts POST requests with {@link UserLoginDto} and returns a JWT token
     * if authentication is successful.
     *
     * @param userLoginDto A DTO containing the user's login credentials.
     * @return A {@link ResponseEntity} containing a {@link JwtResponse} with the JWT token
     *         if authentication is successful, or an UNAUTHORIZED status if authentication fails.
     */
    @PostMapping("")
    public ResponseEntity<?> auth(@RequestBody UserLoginDto userLoginDto) {
        System.out.println("Attempting auth for user: " + userLoginDto.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDto.getUsername(),
                            userLoginDto.getPassword()
                    )
            );

            Instant now = Instant.now();
            long expiry = 36000L;
            String scope = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));

            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("self")
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(expiry))
                    .subject(authentication.getName())
                    .claim("scope", scope)
                    .build();

            String jwtToken = this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
            return ResponseEntity.ok(new JwtResponse(jwtToken));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
