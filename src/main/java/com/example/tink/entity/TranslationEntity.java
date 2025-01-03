package com.example.tink.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
public class TranslationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ipAddress;
    private String inputText;
    private String translatedText;
    private LocalDateTime timestamp;
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
