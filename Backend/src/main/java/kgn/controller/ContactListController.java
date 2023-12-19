package kgn.controller;

import kgn.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/list")
public class ContactListController {

    private final FileService fileService;

    /**
     * Constructs a ContactListController with the given FileService.
     *
     * @param fileService The service used for file operations.
     */
    @Autowired
    public ContactListController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * Retrieves the username of the currently authenticated user.
     *
     * @return Username of the authenticated user.
     */
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
    /**
     * Lists all contact lists for the current user.
     *
     * @return A ResponseEntity containing the list of contact lists.
     */
    @GetMapping
    public ResponseEntity<?> listContactLists() {
        try {
            String username = getCurrentUsername();
            return ResponseEntity.ok(fileService.listContactLists(username));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    /**
     * Creates a new contact list with the specified filename.
     *
     * @param filename The name of the new contact list file.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @PostMapping("/{filename}")
    public ResponseEntity<?> createContactList(@PathVariable String filename) {
        try {
            String username = getCurrentUsername();
            fileService.createContactList(username, filename);
            return ResponseEntity.ok(Map.of("message", "Contact list created successfully."));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Deletes a contact list with the specified filename.
     *
     * @param filename The name of the contact list file to delete.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @DeleteMapping("/{filename}")
    public ResponseEntity<?> deleteContactList(@PathVariable String filename) {
        try {
            String username = getCurrentUsername();
            fileService.deleteContactList(username, filename);
            return ResponseEntity.ok(Map.of("message","Contact list deleted successfully."));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Saves the content of a contact list.
     *
     * @param filename The name of the contact list file to update.
     * @param content The new content of the contact list.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @PutMapping("/{filename}")
    public ResponseEntity<?> saveContactList(@PathVariable String filename, @RequestBody String content) {
        try {
            String username = getCurrentUsername();
            fileService.updateContactList(username, filename, content);
            return ResponseEntity.ok(Map.of("message","Contact list saved successfully."));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Reads the content of a contact list file.
     *
     * @param filename The name of the contact list file to read.
     * @return A ResponseEntity containing the content of the file.
     */
    @GetMapping("/{filename}")
    public ResponseEntity<?> readContactList(@PathVariable String filename) {
        try {
            String username = getCurrentUsername();
            return ResponseEntity.ok(fileService.readFile(username, filename));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}