package translate.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import translate.dto.CustomTranslationRequest;
import translate.dto.CustomTranslationResponse;
import translate.dto.CustomYandexTranslateRequest;
import translate.dto.CustomYandexTranslateResponse;
import translate.entity.CustomTranslation;
import translate.mapper.CustomTranslationMapper;
import translate.repository.CustomTranslationRepository;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class CustomTranslationServiceImpl implements CustomTranslationService {
    private final RestTemplate restTemplate;
    private final ExecutorService executorService;
    private final CustomTranslationRepository customTranslationRepository;
    private final CustomTranslationMapper customTranslationMapper;

    @Value("${YANDEX_FOLDER_ID}")
    private String folderId;

    @Value("${YANDEX_TOKEN}")
    private String apiKey;

    @Autowired
    public CustomTranslationServiceImpl(RestTemplate restTemplate, CustomTranslationRepository customTranslationRepository, CustomTranslationMapper customTranslationMapper) {
        this.restTemplate = restTemplate;
        this.executorService = Executors.newFixedThreadPool(10);
        this.customTranslationRepository = customTranslationRepository;
        this.customTranslationMapper = customTranslationMapper;
    }

    @Override
    public CustomTranslationResponse customTranslate(CustomTranslationRequest body, String ipAddress) throws Exception {
        CustomTranslation translation = customTranslationMapper.mapCustomTranslation(body, ipAddress);
        String translatedText = translateText(body.inputText(), body.sourceLang(), body.targetLang());
        translation.setTranslatedText(translatedText);
        customTranslationRepository.save(translation);
        return customTranslationMapper.mapCustomTranslationToResponse(translation);
    }

    private String translateText(String text, String sourceLang, String targetLang) throws Exception {
        String[] words = text.split("\\s+");
        List<Future<String>> futures = executorService.invokeAll(
                List.of(words).stream().map(word -> (Callable<String>) () -> translateWord(word, sourceLang, targetLang)).collect(Collectors.toList())
        );

        return futures.stream().map(future -> {
            try {
                return future.get();
            } catch (Exception e) {
                return "";
            }
        }).collect(Collectors.joining(" "));
    }

    private String translateWord(String word, String sourceLang, String targetLang) {
        String url = "https://translate.api.cloud.yandex.net/translate/v2/translate";
        CustomYandexTranslateRequest request = new CustomYandexTranslateRequest(sourceLang, targetLang, List.of(word), folderId);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Api-Key " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CustomYandexTranslateRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<CustomYandexTranslateResponse> response = restTemplate.postForEntity(url, entity, CustomYandexTranslateResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && !response.getBody().getTranslations().isEmpty()) {
            return response.getBody().getTranslations().get(0).getText();
        }
        return word;
    }
}







