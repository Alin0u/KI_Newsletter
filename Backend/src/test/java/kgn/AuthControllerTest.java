package kgn;

import kgn.controller.AuthController;
import kgn.dto.JwtResponse;
import kgn.dto.UserLoginDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtEncoder jwtEncoder;

    @InjectMocks
    private AuthController authController;

    @Test
    public void whenValidCredentials_thenReturnsJwtResponse() {
        UserLoginDto loginDto = new UserLoginDto("user", "password", "role");
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        when(authentication.getName()).thenReturn("user");

        Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("name", "user")
                .build();
        when(jwtEncoder.encode(any())).thenReturn(jwt);

        ResponseEntity<?> response = authController.auth(loginDto);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof JwtResponse);
        assertEquals("token", ((JwtResponse) response.getBody()).getToken());
    }
}
