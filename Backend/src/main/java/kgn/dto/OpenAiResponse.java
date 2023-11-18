package kgn.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import java.util.List;

/**
 * The OpenAiResponse class models the response structure expected from the OpenAI API.
 * It is designed to capture the list of completion choices provided in the response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class OpenAiResponse {
    @JsonProperty("choices")
    private List<Choice> choices;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    public static class Choice {
        @JsonProperty("message")
        private Message message;

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Getter
        public static class Message {
            @Getter
            private String content;
        }
    }
}
