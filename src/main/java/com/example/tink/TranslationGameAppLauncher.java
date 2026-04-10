package com.example.tink;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TranslationGameAppLauncher extends Application {

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(TranslationGameAppLauncher.class, args);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TranslationGameApp translationGameApp = context.getBean(TranslationGameApp.class);
        translationGameApp.start(primaryStage);
    }
}
