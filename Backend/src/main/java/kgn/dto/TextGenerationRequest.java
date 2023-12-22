package kgn.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object class for handling text generation requests.
 * This class encapsulates the information needed for generating text,
 * including the name of the model to use and the text prompt to process.
 */
@Getter @Setter
public class TextGenerationRequest {
    public String prompt;
    public String style;
    public String length;
}
