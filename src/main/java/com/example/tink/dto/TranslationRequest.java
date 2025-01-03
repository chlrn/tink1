package com.example.tink.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor

public class TranslationRequest {
    private String folderId;
    private List<String> texts;
    private String sourceLanguageCode;
    private String targetLanguageCode;

    public List<String> getTexts() {
        return texts;
    }
}
