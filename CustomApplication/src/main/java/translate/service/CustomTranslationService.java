package translate.service;

import translate.dto.CustomTranslationRequest;
import translate.dto.CustomTranslationResponse;


public interface CustomTranslationService {
    CustomTranslationResponse customTranslate(CustomTranslationRequest body, String ipAddress) throws Exception;
}
