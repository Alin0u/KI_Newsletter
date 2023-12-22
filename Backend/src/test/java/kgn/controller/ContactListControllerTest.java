package kgn.controller;

import kgn.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ContactListControllerTest {

    @Mock
    private FileService fileService;

    @InjectMocks
    private ContactListController contactListController;

    @BeforeEach
    void setUp() {
        contactListController = new ContactListController(fileService);
    }

    @Test
    @WithMockUser(username = "testUser")
    void createContactListShouldReturnSuccessMessage() throws IOException {
        String filename = "testList.txt";
        doNothing().when(fileService).createContactList(anyString(), eq(filename));

        ResponseEntity<?> response = contactListController.createContactList(filename);

        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(Map.of("message", "Contact list created successfully."), response.getBody());
        verify(fileService, times(1)).createContactList(anyString(), eq(filename));
    }
}