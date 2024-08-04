package translate.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import translate.dto.CustomTranslationRequest;
import translate.dto.CustomTranslationResponse;
import translate.service.CustomTranslationService;
@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Slf4j
public class CustomTranslationController {

    private final CustomTranslationService customTranslationService;

    @PostMapping("/custom-translate")
    public CustomTranslationResponse customTranslate(@RequestBody @Valid CustomTranslationRequest body, HttpServletRequest request) throws Exception {
        String ipAddress = request.getRemoteAddr();
        return customTranslationService.customTranslate(body, ipAddress);
    }
}
