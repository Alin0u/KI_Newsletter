package KGN.KIgestaerkteNewsletter.controller;

import KGN.KIgestaerkteNewsletter.dto.TextGenerationRequest;
import KGN.KIgestaerkteNewsletter.service.NlpService;
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
     * @param request A {@link TextGenerationRequest} object encapsulating the model name and text prompt.
     * @return        The generated text as a string, as returned by the GPT-3.5 Turbo API.
     */
    @PostMapping("/generate")
    public String generateText(@RequestBody TextGenerationRequest request) {
        String model = request.getModelName();
        if (model == null) {
            model = "gpt-3.5-turbo";
        }
        return nlpService.generateText(model, request.getPrompt());
    }
}
