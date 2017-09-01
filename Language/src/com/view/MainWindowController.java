package com.view;

import com.Main;
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
    private Button addNewWord;
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
    }

    @FXML
    private void addNewWord() {

    }

    @FXML
    private void start() {
        if (language.getValue() == null) {
            alert("No language!!!",
                    "The language has not been chosen!!!",
                    "Choose the language motherfucker!!!");
        } else if (category.getValue() == null) {
            alert("No category!!!",
                    "The category has not been chosen",
                    "Choose the category you idiotic piece of shit!!!");
        } else if (reviseType.getValue() == null) {
            alert("No words amount",
                    "The words amount has not been chosen",
                    "Choose the words amount you dunce!!!");
        }
        else {
            setDisables(true);
            clearActionPane();
            wordsAmounts.setText(currentWord + "/" + main.getWordsAmount());
            actionPane.setVisible(true);
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

    private void alert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(main.getPrimaryStage());
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
