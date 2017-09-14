package com;

import com.util.DataSource;
import com.util.ReviseType;
import com.util.Word;
import com.view.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main extends Application {
    private Stage primaryStage;
    private String language;
    private String category;
    private ReviseType reviseType;
    private int wordsAmount;
    private String newLanguage;
    private String newCategory;
    private List<Word> words;

    public static void main(String[] args) {
        launch(args);
        DataSource.backup();
        try {
            DataSource.getStatement().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            DataSource.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        DataSource.connect();
        this.primaryStage = primaryStage;
        primaryStage.setResizable(false);
        primaryStage.setTitle("Mỹ Hân làm tôi cười");
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

    public void showResultsWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Results.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Results");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            ResultsController controller = loader.getController();
            controller.setMain(this);
            controller.setStage(stage);
            controller.setWord(0);
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

    public ReviseType getReviseType() {
        return reviseType;
    }

    public void setReviseType(ReviseType reviseType) {
        this.reviseType = reviseType;
    }

    public int getWordsAmount() {
        return wordsAmount;
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

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
        if (words != null) {
            wordsAmount = words.size();
        } else {
            wordsAmount = 0;
        }
    }
}
