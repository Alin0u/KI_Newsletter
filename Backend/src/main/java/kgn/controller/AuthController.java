package kgn.controller;

import kgn.dto.JwtResponse;
import kgn.dto.UserLoginDto;
import kgn.service.FileService;
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
import java.io.IOException;
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
    private final FileService fileService;

    /**
     * Constructor for AuthController.
     *
     * @param encoder                An instance of {@link JwtEncoder} used for JWT token encoding.
     * @param authenticationManager  An instance of {@link AuthenticationManager} used for user authentication.
     * @param fileService            An instance of {@link FileService} used for file operations.
     */
    public AuthController(JwtEncoder encoder, AuthenticationManager authenticationManager, FileService fileService) {
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.fileService = fileService;
    }

    /**
     * Authenticates a user based on username and password, generates a JWT token, and creates a user directory.
     * This endpoint accepts POST requests with {@link UserLoginDto}, performs authentication, and if successful,
     * returns a JWT token along with creating a user-specific directory for the contact list.
     *
     * @param userLoginDto A DTO containing the user's login credentials.
     * @return A {@link ResponseEntity} containing a {@link JwtResponse} with the JWT token
     *         if authentication is successful. Returns an UNAUTHORIZED status if authentication fails,
     *         or INTERNAL_SERVER_ERROR if user directory creation fails.
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

            if (authentication != null && authentication.isAuthenticated()) {
                try {
                    fileService.createUserDirectory(authentication.getName());
                } catch (IOException e) {
                    System.out.println("Error while creating user directory: " + e.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing user directory");
                }
            }

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
