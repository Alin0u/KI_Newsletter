package KGN.KIgestaerkteNewsletter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for home-related actions.
 * Defines REST endpoint for the home page.
 */
@RestController
public class HomeController {
    // TODO: configure properly
    @GetMapping("/")
    public String home() {
        return "Welcome to KiGestaerkteNewsletter!";
    }
}
