package com.example.tink.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class WordResponse {
    private final String word;

    public WordResponse(String word) {
        this.word = word;
    }


}
