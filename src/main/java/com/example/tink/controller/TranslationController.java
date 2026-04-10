package com.example.tink.controller;

import com.example.tink.dto.TranslationRequest;
import com.example.tink.entity.TranslationEntity;
import com.example.tink.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/translate")
public class TranslationController {

    private final TranslationService translationService;

    @Autowired
    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @PostMapping
    public ResponseEntity<String> translate(
            @RequestBody TranslationRequest requestBody,
            @RequestParam("sourceLanguage") String sourceLanguage,  
            HttpServletRequest request) {

        if (requestBody.getTexts() == null || requestBody.getTexts().isEmpty()) {
            return ResponseEntity.badRequest().body("Текст для перевода отсутствует.");
        }

        String textToTranslate = requestBody.getTexts().get(0);

        List<String> translatedText = translationService.getTranslationToRussian(textToTranslate, sourceLanguage);

        if (translatedText.isEmpty()) {
            return ResponseEntity.internalServerError().body("Ошибка перевода.");
        }

        TranslationEntity translationEntity = new TranslationEntity();
        translationEntity.setIpAddress(request.getRemoteAddr());
        translationEntity.setInputText(textToTranslate);
        translationEntity.setTranslatedText(String.join(", ", translatedText));
        translationEntity.setTimestamp(LocalDateTime.now());
        translationService.saveTranslationRequest(translationEntity);

        return ResponseEntity.ok(String.join(", ", translatedText));
    }
}
