package com.view;

import com.Main;
import com.util.DataSource;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddWordController {
    private Stage stage;
    private Main main;
    @FXML
    private ChoiceBox<String> language;
    @FXML
    private ChoiceBox<String> category;
    @FXML
    private ChoiceBox<String> partOfSpeech;
    @FXML
    private TextField wordInLanguageYouLearn;
    @FXML
    private TextField wordInLanguageYouKnow;

    @FXML
    private void initialize() {
        language.setItems(DataSource.availableLanguages());
        if (!language.getItems().isEmpty()) {
            language.setValue(language.getItems().get(0));
        }
        category.setItems(DataSource.allCategories());
        if (!category.getItems().isEmpty()) {
            category.setValue(category.getItems().get(0));
        }
        partOfSpeech.setItems(DataSource.partsOfSpeech());
        partOfSpeech.setValue(partOfSpeech.getItems().get(0));
    }

    @FXML
    private void addLanguage() {
        main.showAddLanguageWindow();
        if (main.getNewLanguage() != null) {
            language.getItems().add(main.getNewLanguage());
            language.setValue(main.getNewLanguage());
        }
    }

    @FXML
    private void addCategory() {
        main.showAddCategoryWindow();
        if (main.getNewCategory() != null) {
            category.getItems().add(main.getNewCategory());
            category.setValue(main.getNewCategory());
        }
    }

    @FXML
    private void add() {
        if (language.getValue() == null) {
            main.alert("No language!!!",
                    "The language has not been chosen!!!",
                    "Choose the language motherfucker!!!");
        } else if (category.getValue() == null) {
            main.alert("No category!!!",
                    "The category has not been chosen!!!",
                    "Choose the category you idiotic piece of shit!!!");
        } else if (partOfSpeech.getValue() == null) {
            main.alert("No part of speech!!!",
                    "The part of speech has not been chosen!!!",
                    "Choose the part of speech you dunce!!!");
        } else if (wordInLanguageYouLearn.getText() == null) {
            main.alert("No word!!!",
                    "The word field is empty!!!",
                    "What's wrong with you??? Write the word you stupid monkey!!!");
        } else if (wordInLanguageYouKnow.getText() == null) {
            main.alert("No translation!!!",
                    "The translation field is empty!!!",
                    "Are you kidding me??? The word requires a translation you dickhead!!!");
        } else if (DataSource.isWordPresent(wordInLanguageYouLearn.getText(),
                wordInLanguageYouKnow.getText(), language.getValue())) {
            main.alert("Is present!!!",
                    "The word exists already!!!",
                    "Open your eyes!!!");
        } else {
            if (main.getNewLanguage() != null && main.getNewLanguage().equals(language.getValue())) {
                DataSource.insertLanguage(language.getValue());
            }
            if (main.getNewCategory() != null && main.getNewCategory().equals(category.getValue())) {
                DataSource.insertCategory(category.getValue());
            }
            DataSource.insertWord(wordInLanguageYouLearn.getText(), wordInLanguageYouKnow.getText(),
                    language.getValue(), category.getValue(), partOfSpeech.getValue());
            cancel();
        }
    }

    @FXML
    private void cancel() {
        main.setNewCategory(null);
        main.setNewLanguage(null);
        stage.close();
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
