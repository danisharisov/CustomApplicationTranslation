package translate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomYandexTranslateResponse {
    private List<CustomYandexTranslation> translations;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomYandexTranslation {
        private String text;
    }
}

