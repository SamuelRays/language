package com.view;

import com.Main;
import com.util.DataSource;
import com.util.ReviseType;
import com.util.Word;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.LinearGradient;
import javafx.scene.shape.Rectangle;

public class MainWindowController {
    private Main main;
    private static final String CHECK = "Check";
    private static final String NEXT = "Next";
    private int currentWord;
    private int correctAnswersAmount;
    private int wrongAnswersAmount;
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
        reviseType.setItems(FXCollections.observableArrayList(ReviseType.names()));
    }

    private void setLanguages() {
        language.setItems(DataSource.availableLanguages());
        if (!language.getItems().isEmpty()) {
            language.getItems().add(0, "All");
            String lang = language.getItems().get(0);
            language.setValue(lang);
            setCategories(lang);
        }
    }

    private void setCategories(String language) {
        category.setItems(DataSource.availableCategories(language));
        if (!category.getItems().isEmpty()) {
            category.getItems().add(0, "All");
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
            main.setLanguage(language.getValue());
            main.setCategory(category.getValue());
            main.setReviseType(ReviseType.get(reviseType.getValue()));
            main.setWords(DataSource.getWordsList(main.getLanguage(), main.getCategory(), main.getReviseType()));
            if (main.getWordsAmount() == 0) {
                main.alert("No words!!!",
                        "No words for this set of settings!!!",
                        "Try some other settings motherfucker!!!");
                main.setLanguage(null);
                main.setCategory(null);
                main.setReviseType(null);
                main.setWords(null);
            } else {
                setDisables(true);
                clearActionPane();
                actionPane.setVisible(true);
                wordsAmounts.setText(currentWord + "/" + main.getWordsAmount());
                wordToTranslate.setText(main.getWords().get(currentWord).getWord());
            }
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
        if (translation.getText() == null) {
            main.alert("Empty field!!!",
                    "The translation field is empty!!!",
                    "Write something you schmuck!!!");
        } else {
            Word word = main.getWords().get(currentWord);
            word.setUserTranslation(translation.getText());
            boolean isFinished = wordAdd();
            word.incrementUses();
            word.setUsedDate();
            if (translation.getText().equalsIgnoreCase(word.getTranslation())) {
                word.changeRate(true);
                word.incrementCorrects();
                correctAnswersAmount++;
                currentResult.setText("CORRECT");
                currentResult.setTextFill(LinearGradient.valueOf("from 0% 0% to 100% 100%, #00ffff , #ff00ff 100%"));
                currentResult.setVisible(true);
                currentResultArea.setFill(LinearGradient.valueOf("from 0% 0% to 100% 100%, #ff00ff  0% , #00ffff 100%"));
                currentResultArea.setVisible(true);
            } else {
                word.changeRate(false);
                word.incrementWrongs();
                wrongAnswersAmount++;
                currentResult.setText("WRONG");
                currentResult.setTextFill(LinearGradient.valueOf("from 0% 0% to 100% 100%, #ff4000  0% , #ffff00 100%"));
                currentResult.setVisible(true);
                currentResultArea.setFill(LinearGradient.valueOf("from 0% 0% to 100% 100%, #ffff00  0% , #ff4000 100%"));
                currentResultArea.setVisible(true);
            }
            DataSource.update(word);
            if (!isFinished) {
                checkAndNext.setText(NEXT);
            } else {
                checkAndNext.setDisable(true);
                setDisables(false);
                correctAnswers.setText(String.valueOf(correctAnswersAmount));
                wrongAnswers.setText(String.valueOf(wrongAnswersAmount));
                resultsPane.setVisible(true);
            }
        }
    }

    private void next() {
        checkAndNext.setText(CHECK);
        wordToTranslate.setText(main.getWords().get(currentWord).getWord());
        translation.setText(null);
        currentResult.setVisible(false);
        currentResultArea.setVisible(false);
    }

    private boolean wordAdd() {
        currentWord++;
        wordsProgress.setProgress((double) currentWord / main.getWordsAmount());
        return currentWord == main.getWordsAmount();
    }

    private void clearActionPane() {
        currentWord = 0;
        wordsProgress.setProgress(0);
        wordToTranslate.setText(null);
        translation.setText(null);
        checkAndNext.setText(CHECK);
        checkAndNext.setDisable(false);
        currentResultArea.setVisible(false);
        currentResult.setVisible(false);
        resultsPane.setVisible(false);
        correctAnswersAmount = 0;
        wrongAnswersAmount = 0;
        correctAnswers.setText(null);
        wrongAnswers.setText(null);
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
