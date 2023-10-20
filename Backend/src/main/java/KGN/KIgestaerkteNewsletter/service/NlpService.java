package KGN.KIgestaerkteNewsletter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

/**
 * Service class for generating text using the OpenAI GPT-3.5 Turbo API.
 * This service handles the creation and execution of requests to the GPT-3.5 Turbo API,
 * using the provided model name and prompt to generate text.
 *
 * @see <a href="https://gptforwork.com/guides/openai-gpt3-models">Available Models</a>
 * @see <a href="https://platform.openai.com/docs/guides/gpt">OpenAI GPT-3 Guide</a>
 */
@Service
@RequiredArgsConstructor
public class NlpService {
    @Value("${spring.ai.openai.api-key}")
    private String apiToken;

    private final WebClient webClient;

    private final String CHATGPT_API_URL = "https://api.openai.com/v1/chat/completions";

    /**
     * Generates text using the specified model and prompt, by making a request to the OpenAI GPT-3.5 Turbo API.
     * The method constructs the request body, sends the request, and returns the generated text.
     *
     * @param modelName The name of the GPT-3.5 Turbo model to use for text generation.
     * @param prompt    The text prompt to use for text generation.
     * @return          The generated text as a JSON string, as returned by the GPT-3.5 Turbo API.
     * @throws RuntimeException If there is a problem serializing the request body to JSON.
     *
     * Usage:
     * <pre>{@code
     *   // Example curl command for testing:
     *   // curl -X POST http://localhost:8080/api/text-generation/generate -H "Content-Type: application/json" -d "{\"prompt\": \"New collection umbrellas, pink, green, colourful\"}"
     * }</pre>
     */
    public String generateText(String modelName, String prompt) {
        if (apiToken == null) {
            // TODO: get from frontend
        }
        Map<String, Object> requestBody = Map.of(
                "model", modelName,
                "messages", List.of(
                        Map.of("role", "system", "content", "You are a newsletter-specialist who writes Newsletter-Mails out of keywords and/or phrases."),
                        Map.of("role", "user", "content", prompt)
                ),
                "temperature", 0.7
        );
        String requestBodyJson;
        try {
            requestBodyJson = new ObjectMapper().writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize request body to JSON", e);
        }

        String jsonResponse = webClient.post()
                .uri(CHATGPT_API_URL)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiToken)
                .bodyValue(requestBodyJson)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(System.out::println)
                .block();

        return jsonResponse; // TODO: format output
    }
}
