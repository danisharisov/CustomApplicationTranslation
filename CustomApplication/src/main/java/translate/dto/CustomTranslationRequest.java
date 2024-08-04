package translate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record CustomTranslationRequest(
        @NotNull(message = "Введите текст для перевода.")
        String inputText,

        @NotNull(message = "Введите исходный язык.")
        String sourceLang,

        @NotNull(message = "Введите целевой язык.")
        String targetLang
) {
}
