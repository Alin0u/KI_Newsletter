package kgn.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kgn.dto.OpenAiResponse;
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
    public static final String GPT_3_5_TURBO = "gpt-3.5-turbo";
    @Value("${spring.ai.openai.api-key}")
    private String apiToken;

    private final String CHATGPT_API_URL = "https://api.openai.com/v1/chat/completions";

    /**
     * Generates text using the specified model and prompt, by making a request to the OpenAI GPT-3.5 Turbo API.
     * The method constructs the request body, sends the request, and returns the generated text.
     *
     * @param modelName The name of the GPT-3.5 Turbo model to use for text generation.
     * @param prompt    The text prompt to use for text generation.
     * @return The generated text as a JSON string, as returned by the GPT-3.5 Turbo API.
     * @throws RuntimeException If there is a problem serializing the request body to JSON.
     *                          <p>
     *                          Usage:
     *                          <pre>{@code
     *                            // Example curl command for testing:
     *                            // curl -X POST http://localhost:8080/api/text-generation/generate -H "Content-Type: application/json" -d "{\"prompt\": \"New collection umbrellas, pink, green, colourful\"}"
     *                          }</pre>
     */
    public String generateText(String modelName, String prompt, String style, String lenght) {
        if (apiToken == null) {
            // TODO: get from frontend

        }
        Map<String, Object> requestBody = Map.of(
                "model", GPT_3_5_TURBO,
                "messages", List.of(
                        Map.of("role", "system", "content", "You are a newsletter-specialist who writes Newsletter-Mails out of keywords and/or phrases."),
                        Map.of("role", "system", "content", "The newsletter must be formatted in HTML and always needs a subject, marked with 'Subject:'."),
                        Map.of("role", "system", "content", "The tone of the newsletter should be " + style + " and the lenght should be " + lenght + "."),
                        Map.of("role", "system", "content", "Your objective is to generate newsletters that avoid any words or phrases prone to triggering spam filters."),
                        Map.of("role", "system", "content", "Additionally, each newsletter must conclude with a clear option for subscribers to unsubscribe easily. Please include a note at the end of each email"),
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

        if (prompt.contains("test")) {
            // TODO: delete (for testing only)
            return "Subject: Stay Dry in Style with Our Stunning New Umbrella Collection!\n" +
                    "\n" +
                    "Dear valued customer,\n" +
                    "\n" +
                    "We are delighted to introduce our latest collection of umbrellas that will not only keep you dry on those rainy days but also make a fashion statement. Our new line features a wide range of vibrant colors and patterns to add a pop of color to any gloomy weather.\n" +
                    "\n" +
                    "Embrace the beauty of nature with our pink and green umbrellas that perfectly capture the essence of spring. Designed with high-quality materials, these umbrellas are not only visually appealing but also durable, ensuring they will withstand even the harshest weather conditions.\n" +
                    "\n" +
                    "For those who prefer a more playful and fun approach, we have a selection of colorful umbrellas that are sure to brighten up your day. From bold stripes to trendy polka dots, our collection offers a variety of eye-catching designs that will make you stand out from the crowd.\n" +
                    "\n" +
                    "Not only are our umbrellas stylish, but they are also designed with practicality in mind. The canopy is large enough to provide ample coverage, keeping you protected from the rain, while the sturdy frame ensures stability even on windy days. Additionally, our umbrellas are lightweight and compact, making them easy to carry in your bag or briefcase wherever you go.\n" +
                    "\n" +
                    "Whether you need an umbrella for daily use, special occasions, or as a thoughtful gift, our new collection has something for everyone. Visit our website or stop by our store to explore the full range of options and find the perfect umbrella that matches your style and personality.\n" +
                    "\n" +
                    "Don't let the rain dampen your spirits. Embrace the showers with our fabulous new umbrella collection! Stay dry and stylish this season.\n" +
                    "\n" +
                    "Best regards,\n" +
                    "\n" +
                    "[Your Name]\n" +
                    "[Your Company]";
        } else {
            OpenAiResponse openAiResponse = WebClient.create().post()
                    .uri(CHATGPT_API_URL)
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiToken)
                    .bodyValue(requestBodyJson)
                    .retrieve()
                    .bodyToMono(OpenAiResponse.class)
                    .block();
            if (openAiResponse != null && openAiResponse.getChoices() != null && !openAiResponse.getChoices().isEmpty()) {
                OpenAiResponse.Choice.Message message = openAiResponse.getChoices().get(0).getMessage();
                if (message != null) {
                    return message.getContent();
                }
            }
        }

        return "No content available";
    }
}
