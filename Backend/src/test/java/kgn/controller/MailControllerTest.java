package kgn.controller;

import kgn.MailRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class MailControllerTest {

    @InjectMocks
    private MailController mailController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void sendMail_withOneAddress()  {
        // Arrange
        MailRequest mailRequest = new MailRequest(Collections.singletonList("to@example.com"), "Subject", "Text");

        // Act
        ResponseEntity<String> response = mailController.sendMail(mailRequest);

        // Assert
        assertEquals("{\"message\": \"E-Mail erfolgreich gesendet!\"}", response.getBody());

    }

    @Test
    void sendMail_withAddresses()  {
        // Arrange
        List<String> toAddresses = IntStream.range(0, 500)
                .mapToObj(i -> "to" + i + "@example.com")
                .collect(Collectors.toList());

        MailRequest mailRequest = new MailRequest(toAddresses, "Subject", "Text");

        // Act
        ResponseEntity<String> response = mailController.sendMail(mailRequest);

        // Assert
        assertEquals("{\"message\": \"E-Mail erfolgreich gesendet!\"}", response.getBody());

    }
}
