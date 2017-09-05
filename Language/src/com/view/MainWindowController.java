package com.view;

import com.Main;
import com.util.DataSource;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class MainWindowController {
    private Main main;
    private static final String CHECK = "Check";
    private static final String NEXT = "Next";
    private int currentWord;
    @FXML
    private ChoiceBox<String> language;
    @FXML
    private ChoiceBox<String> category;
    @FXML
    private ChoiceBox<String> reviseType;
    @FXML
    private Button start;
    @FXML
    private AnchorPane actionPane;
    @FXML
    private Label wordsAmounts;
    @FXML
    private ProgressBar wordsProgress;
    @FXML
    private TextField wordToTranslate;
    @FXML
    private TextField translation;
    @FXML
    private Rectangle currentResultArea;
    @FXML
    private Label currentResult;
    @FXML
    private Button checkAndNext;
    @FXML
    private AnchorPane resultsPane;
    @FXML
    private Label correctAnswers;
    @FXML
    private Label wrongAnswers;

    @FXML
    private void initialize() {
        actionPane.setVisible(false);
        setLanguages();
        language.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (language.getValue() != null) {
                    setCategories(language.getValue());
                }
            }
        });
    }

    private void setLanguages() {
        language.setItems(DataSource.availableLanguages());
        if (!language.getItems().isEmpty()) {
            String lang = language.getItems().get(0);
            language.setValue(lang);
            setCategories(lang);
        }
    }

    private void setCategories(String language) {
        category.setItems(DataSource.availableCategories(language));
        if (!category.getItems().isEmpty()) {
            category.setValue(category.getItems().get(0));
        }
    }

    @FXML
    private void addNewWord() {
        main.showAddWordWindow();
        setLanguages();
    }

    @FXML
    private void start() {
        if (language.getValue() == null) {
            main.alert("No language!!!",
                    "The language has not been chosen!!!",
                    "Choose the language motherfucker!!!");
        } else if (category.getValue() == null) {
            main.alert("No category!!!",
                    "The category has not been chosen!!!",
                    "Choose the category you idiotic piece of shit!!!");
        } else if (reviseType.getValue() == null) {
            main.alert("No revise type!!!",
                    "The revise type has not been chosen!!!",
                    "Choose the revise type you dunce!!!");
        } else {
            setDisables(true);
            clearActionPane();
            wordsAmounts.setText(currentWord + "/" + main.getWordsAmount());
            actionPane.setVisible(true);
            main.setLanguage(language.getValue());
            main.setCategory(category.getValue());
        }
    }

    @FXML
    private void checkOrNext() {
        if (checkAndNext.getText().equals(CHECK)) {
            check();
        } else if (checkAndNext.getText().equals(NEXT)) {
            next();
        }
    }

    @FXML
    private void showResults() {

    }

    private void check() {
        boolean isFinished = wordAdd();
        if (!isFinished) {
            checkAndNext.setText(NEXT);
        } else {
            checkAndNext.setDisable(true);
            setDisables(false);
            //TODO
        }
    }

    private void next() {
        checkAndNext.setText(CHECK);
        //TODO
    }

    private boolean wordAdd() {
        currentWord++;
        wordsProgress.setProgress((double) currentWord / main.getWordsAmount());
        return currentWord == main.getWordsAmount();
    }

    private void clearActionPane() {
        wordToTranslate.setText(null);
        translation.setText(null);
        checkAndNext.setText(CHECK);
        checkAndNext.setDisable(false);
        currentResultArea.setVisible(false);
        currentResult.setVisible(false);
        resultsPane.setVisible(false);
    }

    private void setDisables(boolean isDisable) {
        language.setDisable(isDisable);
        category.setDisable(isDisable);
        reviseType.setDisable(isDisable);
        start.setDisable(isDisable);
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
