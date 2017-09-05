package com.view;

import com.Main;
import com.util.DataSource;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddLanguageController {
    private Stage stage;
    private Main main;
    @FXML
    private TextField language;

    @FXML
    private void add() {
        if (language.getText().isEmpty()) {
            main.alert("No text!!!",
                    "The field is empty!!!",
                    "What is it? Ghost language? Write something!!!");
        } else if (DataSource.isLanguagePresent(language.getText())) {
            main.alert("Is present!!!",
                    "The language exists already!!!",
                    "Open your eyes!!!");
        } else {
            main.setNewLanguage(language.getText());
            cancel();
        }
    }

    @FXML
    private void cancel() {
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
