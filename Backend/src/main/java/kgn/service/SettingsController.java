package kgn.service;

import kgn.controller.ContactListController;
import kgn.dto.UserSettingsDto;
import kgn.model.User;
import kgn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling user settings.
 * This controller provides endpoints for updating user-specific settings in the application.
 */
@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Updates the settings for the currently authenticated user.
     * Retrieves the current user based on the authenticated user's username and updates their settings.
     *
     * @param settings The new settings to be applied to the user.
     * @throws UsernameNotFoundException if the current username is not found in the database.
     */
    @Transactional
    public void updateUserSettingsForCurrentUser(UserSettingsDto settings) {
        String username = ContactListController.getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        user.setMail(settings.getMail());
        user.setMailserver(settings.getMailserver());
        user.setMailpassword(settings.getMailpassword());
        user.setMailport(settings.getMailport());
        user.setOpenaikey(settings.getOpenaikey());
        user.setSendgridkey(settings.getSendgridkey());

        userRepository.save(user);
    }

    /**
     * REST endpoint to submit new user settings.
     * Accepts a UserSettingsDto object and updates the settings for the current user.
     *
     * @param settings The UserSettingsDto containing the new settings.
     * @return ResponseEntity indicating the result of the operation.
     */
    @PostMapping("/submit")
    public ResponseEntity<?> setSettings(@RequestBody UserSettingsDto settings){
        updateUserSettingsForCurrentUser(settings);
        return ResponseEntity.ok("Settings updated successfully");
    }

}

