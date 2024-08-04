package translate.mapper;

import org.springframework.stereotype.Component;
import translate.dto.CustomTranslationRequest;
import translate.dto.CustomTranslationResponse;
import translate.entity.CustomTranslation;


@Component
public class CustomTranslationMapper {
    public static CustomTranslation mapCustomTranslation(CustomTranslationRequest body, String ipAddress) {
        CustomTranslation translation = new CustomTranslation();
        translation.setInputText(body.inputText());
        translation.setSourceLang(body.sourceLang());
        translation.setTargetLang(body.targetLang());
        translation.setIpAddress(ipAddress);
        return translation;
    }

    public static CustomTranslationResponse mapCustomTranslationToResponse(CustomTranslation translation) {
        return new CustomTranslationResponse(
                translation.getTranslatedText()
        );
    }
}
