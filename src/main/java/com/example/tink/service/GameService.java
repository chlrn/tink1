package com.example.tink.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final TranslationService translationService;

    @Autowired
    public GameService(TranslationService translationService) {
        this.translationService = translationService;
    }

    public String getRandomWord(String language, String level) {
        return translationService.getRandomWord(language, level);
    }

    public boolean checkAnswer(String word, String userTranslation, String userLanguageCode) {
        return translationService.checkAnswer(word, userTranslation, userLanguageCode);
    }

}
