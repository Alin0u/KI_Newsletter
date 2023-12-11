package kgn.controller;

import kgn.service.MailService;
import kgn.service.SendGridService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import jakarta.mail.MessagingException;


class MailControllerTest {

    @Mock
    private MailService mailService;

    @Mock
    private SendGridService sendGridService;

    @InjectMocks
    private MailController mailController;

    @Value("${spring.sendgrid.api-key}")
    private String sendGridApiKey;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendMailWithSendGridApiKeySet() throws MessagingException {

    }

    @Test
    void testSendMailWithSendGridApiKeyNotSet() throws MessagingException {

    }
}
