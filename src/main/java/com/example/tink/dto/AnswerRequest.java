package com.example.tink.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnswerRequest {
    private String word;
    private String translation;
    private String userLanguageCode;  // Новый параметр
    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }

    public String getUserLanguageCode() {
        return userLanguageCode;
    }


}
