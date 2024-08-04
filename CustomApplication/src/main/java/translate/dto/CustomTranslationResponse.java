package translate.dto;

import lombok.Builder;

@Builder
public record CustomTranslationResponse(
        String translatedText
) {

}
