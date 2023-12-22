package kgn.controller;

import kgn.MailRequest;
import kgn.model.User;
import kgn.repository.UserRepository;
import kgn.service.MailService;
import kgn.service.SendGridService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailControllerTest {

    @Mock
    private MailService mailService;

    @Mock
    private SendGridService sendGridService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private MailController mailController;

    @BeforeEach
    void setUp() {
        when(authentication.getName()).thenReturn("testUser");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.setSendgridkey("testSendGridApiKey");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));
    }

    @Test
    void sendMail_withOneAddress()  {
        MailRequest mailRequest = new MailRequest(Collections.singletonList("to@example.com"), "Subject", "Text");

        ResponseEntity<String> response = mailController.sendMail(mailRequest);

        assertEquals("{\"message\": \"E-Mail successfully sent via SendGrid!\"}", response.getBody());
        verify(sendGridService, times(1)).sendBulkEmails(any(), anyString(), anyString());
    }

    @Test
    void sendMail_withAddresses()  {
        List<String> toAddresses = IntStream.range(0, 500)
                .mapToObj(i -> "to" + i + "@example.com")
                .collect(Collectors.toList());

        MailRequest mailRequest = new MailRequest(toAddresses, "Subject", "Text");

        ResponseEntity<String> response = mailController.sendMail(mailRequest);

        assertEquals("{\"message\": \"E-Mail successfully sent via SendGrid!\"}", response.getBody());
        verify(sendGridService, times(1)).sendBulkEmails(any(), anyString(), anyString());
    }
}
