package com.view;

import com.Main;
import com.util.DataSource;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCategoryController {
    private Stage stage;
    private Main main;
    @FXML
    private TextField category;

    @FXML
    private void add() {
        if (category.getText().isEmpty()) {
            main.alert("No text!!!",
                    "The field is empty!!!",
                    "What is it? Ghost category? Write something!!!");
        } else if (DataSource.isCategoryPresent(category.getText())) {
            main.alert("Is present!!!",
                    "The category exists already!!!",
                    "Open your eyes!!!");
        } else {
            main.setNewCategory(category.getText());
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
