package com.example.tink;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TranslationGameAppLauncher extends Application {

    // Контекст Spring, используемый для запуска приложения
    private static ConfigurableApplicationContext context;

    // Главный метод для запуска Spring Boot приложения
    public static void main(String[] args) {
        context = SpringApplication.run(TranslationGameAppLauncher.class, args);
        launch(args);
    }

    // Метод для запуска JavaFX UI
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Получаем компонент TranslationGameApp из Spring контекста
        TranslationGameApp translationGameApp = context.getBean(TranslationGameApp.class);
        translationGameApp.start(primaryStage);
    }
}
