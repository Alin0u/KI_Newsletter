package kgn.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

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
