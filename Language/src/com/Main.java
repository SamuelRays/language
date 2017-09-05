package com;

import com.util.DataSource;
import com.view.AddCategoryController;
import com.view.AddLanguageController;
import com.view.AddWordController;
import com.view.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;
    private String language;
    private String category;
    private int wordsAmount;
    private String newLanguage;
    private String newCategory;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DataSource.connect();
        this.primaryStage = primaryStage;
        primaryStage.setResizable(false);
        primaryStage.setTitle("Mỹ Hân rất quan trọng");
        initMainWindow();
    }

    public void initMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/MainWindow.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            MainWindowController controller = loader.getController();
            controller.setMain(this);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddWordWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/AddWord.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Adding new word");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            AddWordController controller = loader.getController();
            controller.setMain(this);
            controller.setStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddLanguageWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/AddLanguage.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Adding new language");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            AddLanguageController controller = loader.getController();
            controller.setMain(this);
            controller.setStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddCategoryWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/AddCategory.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Adding new category");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            AddCategoryController controller = loader.getController();
            controller.setMain(this);
            controller.setStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void alert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(primaryStage);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNewLanguage() {
        return newLanguage;
    }

    public void setNewLanguage(String newLanguage) {
        this.newLanguage = newLanguage;
    }

    public String getNewCategory() {
        return newCategory;
    }

    public void setNewCategory(String newCategory) {
        this.newCategory = newCategory;
    }

    public void setWordsAmount() {

    }

    public int getWordsAmount() {
        return wordsAmount;
    }
}
