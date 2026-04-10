package com.example.tink.service;

import com.example.tink.entity.TranslationEntity;
import com.example.tink.repository.TranslationRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class TranslationService {

    private final RestTemplate restTemplate;
    private final String apiKey = "YOUR_APIKEY";
    private final String folderId = "YOUR_FOLDERID";

    private final Map<String, String> words = new ConcurrentHashMap<>();
    private final Random random = new Random();
    private final TranslationRepository translationRepository;

    @Autowired
    public TranslationService(RestTemplateBuilder restTemplateBuilder, TranslationRepository translationRepository) {
        this.restTemplate = restTemplateBuilder.build();
        this.translationRepository = translationRepository;

        initializeWords();
    }

    private void initializeWords() {
        words.put("hello_en_A1", "hello");
        words.put("goodbye_en_A1", "goodbye");
        words.put("book_en_A1", "book");
        words.put("cat_en_A1", "cat");
        words.put("dog_en_A1", "dog");
        words.put("table_en_A1", "table");
        words.put("pen_en_A1", "pen");
        words.put("apple_en_A1", "apple");
        words.put("ball_en_A1", "ball");
        words.put("sun_en_A1", "sun");
        words.put("chair_en_A1", "chair");
        words.put("water_en_A1", "water");
        words.put("food_en_A1", "food");
        words.put("friend_en_A1", "friend");
        words.put("house_en_A1", "house");

        words.put("hello_en_A2", "hello");
        words.put("car_en_A2", "car");
        words.put("school_en_A2", "school");
        words.put("computer_en_A2", "computer");
        words.put("bookstore_en_A2", "bookstore");
        words.put("family_en_A2", "family");
        words.put("teacher_en_A2", "teacher");
        words.put("city_en_A2", "city");
        words.put("schoolbag_en_A2", "schoolbag");
        words.put("homework_en_A2", "homework");
        words.put("kitchen_en_A2", "kitchen");
        words.put("office_en_A2", "office");
        words.put("garden_en_A2", "garden");
        words.put("bicycle_en_A2", "bicycle");
        words.put("weather_en_A2", "weather");

        words.put("opinion_en_B1", "opinion");
        words.put("environment_en_B1", "environment");
        words.put("health_en_B1", "health");
        words.put("holiday_en_B1", "holiday");
        words.put("conversation_en_B1", "conversation");
        words.put("language_en_B1", "language");
        words.put("exercise_en_B1", "exercise");
        words.put("presentation_en_B1", "presentation");
        words.put("interview_en_B1", "interview");
        words.put("government_en_B1", "government");
        words.put("population_en_B1", "population");
        words.put("pollution_en_B1", "pollution");
        words.put("discussion_en_B1", "discussion");
        words.put("program_en_B1", "program");
        words.put("problem_en_B1", "problem");

        words.put("challenging_en_B2", "challenging");
        words.put("consequence_en_B2", "consequence");
        words.put("achievement_en_B2", "achievement");
        words.put("responsibility_en_B2", "responsibility");
        words.put("analysis_en_B2", "analysis");
        words.put("intermediate_en_B2", "intermediate");
        words.put("situation_en_B2", "situation");
        words.put("solution_en_B2", "solution");
        words.put("experience_en_B2", "experience");
        words.put("strategy_en_B2", "strategy");
        words.put("development_en_B2", "development");
        words.put("opportunity_en_B2", "opportunity");
        words.put("research_en_B2", "research");
        words.put("decision_en_B2", "decision");
        words.put("confidence_en_B2", "confidence");

        words.put("complicated_en_C1", "complicated");
        words.put("cooperation_en_C1", "cooperation");
        words.put("management_en_C1", "management");
        words.put("perspective_en_C1", "perspective");
        words.put("creativity_en_C1", "creativity");
        words.put("flexibility_en_C1", "flexibility");
        words.put("contribution_en_C1", "contribution");
        words.put("alternative_en_C1", "alternative");
        words.put("evaluation_en_C1", "evaluation");
        words.put("comparison_en_C1", "comparison");
        words.put("motivation_en_C1", "motivation");
        words.put("concentration_en_C1", "concentration");
        words.put("implementation_en_C1", "implementation");
        words.put("solution_en_C1", "solution");
        words.put("sustainability_en_C1", "sustainability");

        words.put("sophisticated_en_C2", "sophisticated");
        words.put("innovative_en_C2", "innovative");
        words.put("collaboration_en_C2", "collaboration");
        words.put("specialized_en_C2", "specialized");
        words.put("synthesis_en_C2", "synthesis");
        words.put("evaluation_en_C2", "evaluation");
        words.put("cognition_en_C2", "cognition");
        words.put("adaptation_en_C2", "adaptation");
        words.put("metaphor_en_C2", "metaphor");
        words.put("nuance_en_C2", "nuance");
        words.put("hypothesis_en_C2", "hypothesis");
        words.put("abstract_en_C2", "abstract");
        words.put("paradox_en_C2", "paradox");
        words.put("discrepancy_en_C2", "discrepancy");
        words.put("ambiguity_en_C2", "ambiguity");

        // Пример слов для немецкого языка
        words.put("hallo_de_A1", "hallo");
        words.put("freund_de_A1", "freund");
        words.put("haus_de_A1", "haus");
        words.put("katze_de_A1", "katze");
        words.put("hund_de_A1", "hund");
        words.put("apfel_de_A1", "apfel");
        words.put("tisch_de_A1", "tisch");
        words.put("buch_de_A1", "buch");
        words.put("wasser_de_A1", "wasser");
        words.put("sonne_de_A1", "sonne");
        words.put("stuhl_de_A1", "stuhl");
        words.put("ball_de_A1", "ball");
        words.put("schulbuch_de_A1", "schulbuch");
        words.put("lehrer_de_A1", "lehrer");
        words.put("schreiben_de_A1", "schreiben");

        words.put("computer_de_A2", "computer");
        words.put("schule_de_A2", "schule");
        words.put("buchhandlung_de_A2", "buchhandlung");
        words.put("stunde_de_A2", "stunde");
        words.put("auto_de_A2", "auto");
        words.put("stadt_de_A2", "stadt");
        words.put("schule_de_A2", "schule");
        words.put("garten_de_A2", "garten");
        words.put("woche_de_A2", "woche");
        words.put("abenteuer_de_A2", "abenteuer");
        words.put("musik_de_A2", "musik");
        words.put("sprache_de_A2", "sprache");
        words.put("urlaub_de_A2", "urlaub");
        words.put("restaurant_de_A2", "restaurant");
        words.put("freundschaft_de_A2", "freundschaft");

        words.put("analyse_de_B1", "analyse");
        words.put("umwelt_de_B1", "umwelt");
        words.put("interview_de_B1", "interview");
        words.put("regierung_de_B1", "regierung");
        words.put("diskussion_de_B1", "diskussion");
        words.put("meinung_de_B1", "meinung");
        words.put("praktikum_de_B1", "praktikum");
        words.put("gesundheit_de_B1", "gesundheit");
        words.put("problem_de_B1", "problem");
        words.put("umweltdebatte_de_B1", "umweltdebatte");
        words.put("arbeitsplatz_de_B1", "arbeitsplatz");
        words.put("zukunft_de_B1", "zukunft");
        words.put("gesellschaft_de_B1", "gesellschaft");
        words.put("verantwortung_de_B1", "verantwortung");
        words.put("forschung_de_B1", "forschung");

        words.put("erfolg_de_B2", "erfolg");
        words.put("analyse_de_B2", "analyse");
        words.put("problemdebatte_de_B2", "problemdebatte");
        words.put("wirkung_de_B2", "wirkung");
        words.put("abstrakt_de_B2", "abstrakt");
        words.put("entscheidung_de_B2", "entscheidung");
        words.put("verantwortung_de_B2", "verantwortung");
        words.put("programm_de_B2", "programm");
        words.put("perspektive_de_B2", "perspektive");
        words.put("konflikt_de_B2", "konflikt");
        words.put("strategien_de_B2", "strategien");
        words.put("reflexion_de_B2", "reflexion");
        words.put("prozess_de_B2", "prozess");
        words.put("beziehung_de_B2", "beziehung");
        words.put("gemeinde_de_B2", "gemeinde");

        words.put("komplex_de_C1", "komplex");
        words.put("intellektuell_de_C1", "intellektuell");
        words.put("diskussion_de_C1", "diskussion");
        words.put("entwicklung_de_C1", "entwicklung");
        words.put("ansatz_de_C1", "ansatz");
        words.put("management_de_C1", "management");
        words.put("kompliziert_de_C1", "kompliziert");
        words.put("analyse_de_C1", "analyse");
        words.put("kreativ_de_C1", "kreativ");
        words.put("strategisch_de_C1", "strategisch");
        words.put("bewertung_de_C1", "bewertung");
        words.put("voraussetzung_de_C1", "voraussetzung");
        words.put("konzeption_de_C1", "konzeption");
        words.put("kooperation_de_C1", "kooperation");
        words.put("konsistenz_de_C1", "konsistenz");

        words.put("spezialisiert_de_C2", "spezialisiert");
        words.put("innovativ_de_C2", "innovativ");
        words.put("forschung_de_C2", "forschung");
        words.put("abstraktion_de_C2", "abstraktion");
        words.put("zukunftsorientiert_de_C2", "zukunftsorientiert");
        words.put("komplexität_de_C2", "komplexität");
        words.put("relevanz_de_C2", "relevanz");
        words.put("theorie_de_C2", "theorie");
        words.put("interpretation_de_C2", "interpretation");
        words.put("revolutionär_de_C2", "revolutionär");
        words.put("multidimensional_de_C2", "multidimensional");
        words.put("entwicklung_de_C2", "entwicklung");
        words.put("kompetenz_de_C2", "kompetenz");
        words.put("interdisziplinär_de_C2", "interdisziplinär");
        words.put("methodologie_de_C2", "methodologie");

        
        words.put("bonjour_fr_A1", "bonjour");  
        words.put("au revoir_fr_A1", "au revoir");  
        words.put("chat_fr_A1", "chat");  
        words.put("chien_fr_A1", "chien");  
        words.put("maison_fr_A1", "maison"); 
        words.put("table_fr_A1", "table");  
        words.put("stylo_fr_A1", "stylo");  
        words.put("pomme_fr_A1", "pomme");  
        words.put("ballon_fr_A1", "ballon");  
        words.put("soleil_fr_A1", "soleil");  
        words.put("chaise_fr_A1", "chaise");  
        words.put("eau_fr_A1", "eau");  
        words.put("ami_fr_A1", "ami");  
        words.put("voiture_fr_A1", "voiture");  
        words.put("fruit_fr_A1", "fruit");  

        words.put("école_fr_A2", "école");  
        words.put("livre_fr_A2", "livre");  
        words.put("ordinateur_fr_A2", "ordinateur");  
        words.put("famille_fr_A2", "famille");  
        words.put("bureau_fr_A2", "bureau");  
        words.put("jardin_fr_A2", "jardin");  
        words.put("vélo_fr_A2", "vélo");  
        words.put("repas_fr_A2", "repas");  
        words.put("fête_fr_A2", "fête");  
        words.put("maison_fr_A2", "maison");  
        words.put("cadeau_fr_A2", "cadeau");  
        words.put("chanson_fr_A2", "chanson");  
        words.put("travail_fr_A2", "travail");  
        words.put("écrire_fr_A2", "écrire");  
        words.put("voisin_fr_A2", "voisin");  

        words.put("environnement_fr_B1", "environnement");  
        words.put("santé_fr_B1", "santé");  
        words.put("vacances_fr_B1", "vacances");  
        words.put("conversation_fr_B1", "conversation");  
        words.put("langue_fr_B1", "langue");  
        words.put("exercice_fr_B1", "exercice");  
        words.put("présentation_fr_B1", "présentation"); 
        words.put("interview_fr_B1", "interview");  
        words.put("gouvernement_fr_B1", "gouvernement");  
        words.put("pollution_fr_B1", "pollution"); 
        words.put("discussions_fr_B1", "discussions");  
        words.put("éducation_fr_B1", "éducation");  
        words.put("communauté_fr_B1", "communauté");  
        words.put("problème_fr_B1", "problème");  
        words.put("emploi_fr_B1", "emploi");  

        // Уровень B2
        words.put("conséquence_fr_B2", "conséquence");  
        words.put("réflexion_fr_B2", "réflexion");  
        words.put("opportunité_fr_B2", "opportunité");  
        words.put("situation_fr_B2", "situation");  
        words.put("solution_fr_B2", "solution");  
        words.put("stratégie_fr_B2", "stratégie");  
        words.put("développement_fr_B2", "développement");  
        words.put("programme_fr_B2", "programme"); 
        words.put("réalisation_fr_B2", "réalisation");  
        words.put("évaluation_fr_B2", "évaluation");  
        words.put("études_fr_B2", "études");  
        words.put("approche_fr_B2", "approche");  
        words.put("recherche_fr_B2", "recherche");  
        words.put("décision_fr_B2", "décision");  
        words.put("équipe_fr_B2", "équipe");  

        // Уровень C1
        words.put("complication_fr_C1", "complication");  
        words.put("gestion_fr_C1", "gestion");  
        words.put("perspective_fr_C1", "perspective");  
        words.put("créativité_fr_C1", "créativité");  
        words.put("flexibilité_fr_C1", "flexibilité");  
        words.put("contribution_fr_C1", "contribution");  
        words.put("alternative_fr_C1", "alternative");  
        words.put("évaluation_fr_C1", "évaluation");  
        words.put("motivation_fr_C1", "motivation");  
        words.put("concentration_fr_C1", "concentration");  
        words.put("collaboration_fr_C1", "collaboration");  
        words.put("soutien_fr_C1", "soutien");  
        words.put("projet_fr_C1", "projet");  
        words.put("défi_fr_C1", "défi");  
        words.put("avantage_fr_C1", "avantage");  

        // Уровень C2
        words.put("sophistiqué_fr_C2", "sophistiqué");  
        words.put("innovant_fr_C2", "innovant");  
        words.put("collaboration_fr_C2", "collaboration");  
        words.put("interprétation_fr_C2", "interprétation");  
        words.put("analyse_fr_C2", "analyse");  
        words.put("abstraction_fr_C2", "abstraction");  
        words.put("connaissance_fr_C2", "connaissance");  
        words.put("nuance_fr_C2", "nuance");  
        words.put("réflexion_fr_C2", "réflexion");  
        words.put("dilemme_fr_C2", "dilemme");  
        words.put("paradoxe_fr_C2", "paradoxe");  
        words.put("hypothèse_fr_C2", "hypothèse");  
        words.put("innovation_fr_C2", "innovation");  
        words.put("recherche_fr_C2", "recherche");  
        words.put("complexité_fr_C2", "complexité");  
    }

    public List<String> getTranslationToRussian(String word, String sourceLanguageCode) {
        String url = "https://translate.api.cloud.yandex.net/translate/v2/translate";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "API-key " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        sourceLanguageCode = sourceLanguageCode.substring(0, 2).toLowerCase();

        JSONObject requestBody = new JSONObject();
        requestBody.put("texts", new JSONArray().put(word));
        requestBody.put("folderId", folderId);
        requestBody.put("targetLanguageCode", sourceLanguageCode); 
        requestBody.put("sourceLanguageCode", "ru"); 


        System.out.println("Заголовки: " + headers);
        System.out.println("Тело запроса: " + requestBody.toString());

        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            System.out.println("Ответ от API: " + response.getStatusCode() + " - " + response.getBody());

            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject jsonResponse = new JSONObject(response.getBody());
                JSONArray translations = jsonResponse.getJSONArray("translations");

                List<String> translationsList = new ArrayList<>();
                for (int i = 0; i < translations.length(); i++) {
                    translationsList.add(translations.getJSONObject(i).getString("text"));
                }
                return translationsList;
            } else {
                System.out.println("Ошибка при запросе перевода: " + response.getStatusCode());
                return new ArrayList<>();
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка при обращении к API перевода: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public List<String> getWordsForLevel(String language, String level) {
        if (language == null || level == null) {
            System.out.println("Ошибка: язык или уровень пусты");
            return new ArrayList<>();  
        }

        String languageCode = language.substring(0, 2).toLowerCase();
        String keyPrefix = languageCode + "_" + level.toUpperCase();

        System.out.println("Ищем слова с префиксом: " + keyPrefix);

        List<String> filteredWords = words.entrySet().stream()
                .filter(entry -> entry.getKey().contains(keyPrefix))  
                .map(Map.Entry::getValue)  
                .collect(Collectors.toList());

        if (filteredWords.isEmpty()) {
            System.out.println("Не найдено слов с префиксом: " + keyPrefix);
        } else {
            System.out.println("Найденные слова: " + filteredWords);
        }

        return filteredWords;
    }



    public String getRandomWord(String language, String level) {
        List<String> wordsForLevel = getWordsForLevel(language, level);
        if (wordsForLevel.isEmpty()) {
            System.out.println("Ошибка: не найдено слов для уровня " + level + " и языка " + language);
            return null;
        }

        int randomIndex = new Random().nextInt(wordsForLevel.size());
        return wordsForLevel.get(randomIndex);
    }


    public String translate(String input, String sourceLanguageCode, String targetLanguageCode) {
        String apiUrl = "https://translate.api.cloud.yandex.net/translate/v2/translate";
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("folderId", folderId);
            requestBody.put("texts", new JSONArray().put(input));
            requestBody.put("targetLanguageCode", targetLanguageCode);
            requestBody.put("sourceLanguageCode", sourceLanguageCode);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Api-Key " + apiKey);

            HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject responseBody = new JSONObject(response.getBody());
                JSONArray translations = responseBody.getJSONArray("translations");
                if (translations.length() > 0) {
                    return translations.getJSONObject(0).getString("text");
                } else {
                    throw new RuntimeException("Translation not found in response");
                }
            } else {
                throw new RuntimeException("Failed to translate text: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create request body", e);
        }
    }

    public boolean checkAnswer(String word, String userTranslation, String userLanguageCode) {
        String correctTranslation = translate(word, "en", userLanguageCode);
        return correctTranslation.equalsIgnoreCase(userTranslation);
    }
    public String getTranslation(String word, String sourceLanguageCode) {
        List<String> translations = getTranslationToRussian(word, sourceLanguageCode);
        return translations.isEmpty() ? null : translations.get(0); 
    }



    // Метод для сохранения перевода в базу данных
    public void saveTranslationRequest(TranslationEntity translationEntity) {
        translationRepository.save(translationEntity);
    }
}
