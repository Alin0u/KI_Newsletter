package kgn.controller;

import kgn.dto.TextGenerationRequest;
import kgn.service.NlpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling text generation requests.
 * This controller defines an endpoint for generating text using the GPT-3.5 Turbo API,
 * leveraging the {@link NlpService} for interacting with the API.
 */
@RestController
@RequestMapping("/api/text-generation")
@RequiredArgsConstructor
public class TextGenerationController {
    private final NlpService nlpService;

    /**
     * Generates text using the specified model and prompt, by making a request to the API.
     * If no model name is provided in the request, the method defaults to using "gpt-3.5-turbo".
     *
     */

    @PostMapping("/generate")
    public String generateText(@RequestBody TextGenerationRequest request ) {
        String defaultModel = "gpt-3.5-turbo";
        return nlpService.generateText(defaultModel, request.getPrompt(), request.getStyle(), request.getLength());
    }
}
