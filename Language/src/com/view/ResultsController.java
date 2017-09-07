package com.view;

import com.Main;
import com.util.Result;
import com.util.Word;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ResultsController {
    private Stage stage;
    private Main main;
    private int currentNumber;
    @FXML
    private Label wordNumber;
    @FXML
    private TextField word;
    @FXML
    private TextField correctTranslation;
    @FXML
    private TextField yourTranslation;
    @FXML
    private Label currentResult;
    @FXML
    private Rectangle currentResultArea;
    @FXML
    private Button previous;
    @FXML
    private Button next;

    @FXML
    private void initialize() {

    }

    @FXML
    private void next() {
        setWord(++currentNumber);
    }

    @FXML
    private void previous() {
        setWord(--currentNumber);
    }

    @FXML
    private void close() {
        stage.close();
    }

    public void setWord(int number) {
        if (number == 0) {
            previous.setDisable(true);
        } else if (number == 1) {
            previous.setDisable(false);
        }
        if (number == main.getWordsAmount() - 1) {
            next.setDisable(true);
        } else if (number == main.getWordsAmount() - 2) {
            next.setDisable(false);
        }
        Word w = main.getWords().get(number);
        wordNumber.setText("Word #" + (number + 1));
        word.setText(w.getTranslation());
        correctTranslation.setText(w.getWord());
        yourTranslation.setText(w.getUserTranslation());
        if (w.getCorrects() != 0) {
            currentResult.setText(Result.CORRECT.getName());
            currentResult.setTextFill(Result.CORRECT.getTextPaint());
            currentResultArea.setFill(Result.CORRECT.getFieldPaint());
        } else {
            currentResult.setText(Result.WRONG.getName());
            currentResult.setTextFill(Result.WRONG.getTextPaint());
            currentResultArea.setFill(Result.WRONG.getFieldPaint());
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
