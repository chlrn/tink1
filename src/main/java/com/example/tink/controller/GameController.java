package com.example.tink.controller;

import com.example.tink.dto.WordResponse;
import com.example.tink.dto.AnswerRequest;
import com.example.tink.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final TranslationService translationService;

    @Autowired
    public GameController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @GetMapping("/word")
    public ResponseEntity<WordResponse> getWord(@RequestParam String language, @RequestParam String level) {
        String randomWord = translationService.getRandomWord(language, level);
        return ResponseEntity.ok(new WordResponse(randomWord));
    }

    @PostMapping("/check")
    public ResponseEntity<Boolean> checkAnswer(@RequestBody AnswerRequest answerRequest) {
        boolean isCorrect = translationService.checkAnswer(answerRequest.getWord(), answerRequest.getTranslation(), answerRequest.getUserLanguageCode());
        return ResponseEntity.ok(isCorrect);
    }
}
