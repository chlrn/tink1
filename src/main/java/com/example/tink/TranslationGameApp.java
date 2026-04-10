package com.example.tink;

import com.example.tink.service.TranslationService;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class TranslationGameApp {

    private final TranslationService translationService;
    private String correctAnswer;
    private int score = 0;
    private final Random random = new Random();
    private int currentWordIndex = 0;
    private List<String> wordsList;

    private String selectedLanguage;
    private String selectedLevel;

    private final ImageView feedbackImageView = new ImageView();

    @Autowired
    public TranslationGameApp(TranslationService translationService) {
        this.translationService = translationService;
    }

    public void start(Stage primaryStage) {
        ComboBox<String> languageComboBox = new ComboBox<>();
        languageComboBox.getItems().addAll("English", "German", "French");

        ComboBox<String> levelComboBox = new ComboBox<>();
        levelComboBox.getItems().addAll("A1", "A2", "B1", "B2", "C1", "C2");

        Button startButton = new Button("Start Game");

        Label scoreLabel = new Label("Score: 0");
        StackPane scorePane = createLabeledPane(scoreLabel, 150, 30, Color.GRAY);

        Label wordLabel = new Label("Word:");
        StackPane wordPane = createLabeledPane(wordLabel, 300, 40, Color.LIGHTGRAY);

        ToggleGroup optionsGroup = new ToggleGroup();
        VBox optionsBox = new VBox(10);
        optionsBox.setAlignment(Pos.CENTER);

        for (int i = 0; i < 3; i++) {
            RadioButton option = new RadioButton();
            option.setToggleGroup(optionsGroup);
            StackPane optionPane = createLabeledPane(option, 200, 40, Color.LIGHTBLUE);
            optionsBox.getChildren().add(optionPane);
        }

        Button submitButton = new Button("Submit");

        startButton.setOnAction(e -> {
            selectedLanguage = languageComboBox.getValue();
            selectedLevel = levelComboBox.getValue();

            if (selectedLanguage == null || selectedLevel == null) {
                System.out.println("Ошибка: выберите язык и уровень.");
                return;
            }

            startGame(selectedLanguage, selectedLevel, wordLabel, optionsBox, scoreLabel, primaryStage);
        });

        submitButton.setOnAction(e -> {
            RadioButton selectedOption = (RadioButton) optionsGroup.getSelectedToggle();
            if (selectedOption != null) {
                checkAnswer(selectedOption.getText(), wordLabel, optionsBox, scoreLabel, primaryStage);
            } else {
                System.out.println("Выберите вариант ответа!");
            }
        });

        VBox layout = new VBox(10, languageComboBox, levelComboBox, startButton, wordPane, optionsBox, submitButton, scorePane);
        layout.setAlignment(Pos.CENTER);

        // Устанавливаем фоновое изображение
        ImageView backgroundImage = new ImageView(new Image(getClass().getResource("/images/1.png").toExternalForm()));
        backgroundImage.setFitWidth(600);
        backgroundImage.setFitHeight(600);

        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImage, layout, feedbackImageView);

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private StackPane createLabeledPane(Labeled labeled, double width, double height, Color color) {
        Rectangle background = new Rectangle(width, height, color);
        StackPane pane = new StackPane(background, labeled);
        pane.setAlignment(Pos.CENTER);
        return pane;
    }

    private void startGame(String language, String level, Label wordLabel, VBox optionsBox, Label scoreLabel, Stage primaryStage) {
        wordsList = translationService.getWordsForLevel(language, level);
        if (wordsList.isEmpty()) {
            System.out.println("Ошибка: не удалось получить список слов.");
            return;
        }

        // Если в списке слов меньше 15, добавляем до 15 (если это необходимо)
        if (wordsList.size() < 15) {
            System.out.println("Ошибка: недостаточно слов в списке.");
            return;
        }

        currentWordIndex = 0;
        score = 0;
        scoreLabel.setText("Score: 0");
        showNextWord(wordLabel, optionsBox);
    }

    private void showNextWord(Label wordLabel, VBox optionsBox) {
        if (currentWordIndex >= 15) { 
            finishGame(wordLabel);
            return;
        }

        String word = wordsList.get(currentWordIndex);
        String sourceLanguageCode = getLanguageCode(selectedLanguage);
        String targetLanguageCode = "ru"; 

        correctAnswer = translationService.translate(word, sourceLanguageCode, targetLanguageCode);
        wordLabel.setText("Переведите: " + word);

        List<String> options = generateOptions(correctAnswer);
        for (int i = 0; i < options.size(); i++) {
            StackPane optionPane = (StackPane) optionsBox.getChildren().get(i);
            RadioButton optionButton = (RadioButton) optionPane.getChildren().get(1); 
            optionButton.setText(options.get(i)); 
        }
    }

    private List<String> generateOptions(String correctAnswer) {
        List<String> options = new ArrayList<>();
        options.add(correctAnswer);

        while (options.size() < 3) {
            String randomWord = translationService.getRandomWord(selectedLanguage, selectedLevel);
            String randomTranslation = translationService.translate(randomWord, getLanguageCode(selectedLanguage), "ru");

            if (!options.contains(randomTranslation)) {
                options.add(randomTranslation);
            }
        }

        Collections.shuffle(options);
        return options;
    }

    private void checkAnswer(String selectedAnswer, Label wordLabel, VBox optionsBox, Label scoreLabel, Stage primaryStage) {
        if (selectedAnswer.equals(correctAnswer)) {
            if (feedbackImageView.getImage() == null) {
                score++;
                feedbackImageView.setImage(new Image(getClass().getResource("/images/2.png").toExternalForm()));
            }
        } else {
            feedbackImageView.setImage(new Image(getClass().getResource("/images/3.png").toExternalForm()));
        }

        scoreLabel.setText("Score: " + score);

        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.setOnFinished(event -> {
            feedbackImageView.setImage(null);
            currentWordIndex++;
            showNextWord(wordLabel, optionsBox);
        });
        pause.play();
    }

    private void finishGame(Label wordLabel) {
        double correctRatio = (double) score / 15; 

        StackPane resultRoot = new StackPane();


        String finalImagePath;
        if (score == 15) {
            finalImagePath = "/images/perfect.png";
        } else if (score > 7) {
            finalImagePath = "/images/good.png";
        } else {
            finalImagePath = "/images/try_again.png";
        }

        feedbackImageView.setImage(new Image(getClass().getResource(finalImagePath).toExternalForm()));
        resultRoot.getChildren().add(feedbackImageView);

        Label resultLabel = new Label("Your score: " + score + "/15");
        resultLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: gray;");
        resultRoot.getChildren().add(resultLabel);

        StackPane.setAlignment(feedbackImageView, Pos.CENTER);
        StackPane.setAlignment(resultLabel, Pos.TOP_CENTER);

        Scene resultScene = new Scene(resultRoot, 600, 600);
        Stage resultStage = new Stage();
        resultStage.setScene(resultScene);
        resultStage.show();
    }

    private String getLanguageCode(String language) {
        switch (language) {
            case "English":
                return "en";
            case "German":
                return "de";
            case "French":
                return "fr";
            default:
                throw new IllegalArgumentException("Неизвестный язык: " + language);
        }
    }
}
