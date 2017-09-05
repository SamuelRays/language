package com.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class DataSource {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/words";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root";
    private static Connection connection;
    private static Statement statement;

    public static void connect() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<String> availableLanguages() {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            ResultSet set = statement.executeQuery("SELECT language FROM languages");
            while (set.next()) {
                list.add(set.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ObservableList<String> availableCategories(String language) {
        Set<String> strings = new HashSet<>();
        try {
            ResultSet categorySet = statement.executeQuery("SELECT category FROM categories");
            while (categorySet.next()) {
                strings.add(categorySet.getString(1));
            }
            ResultSet partOfSpeechSet = statement.executeQuery("SELECT part_of_speech FROM main WHERE language = \""
                    + language + "\"");
            while (partOfSpeechSet.next()) {
                strings.add(partOfSpeechSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(strings);
    }

    public static ObservableList<String> allCategories() {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            ResultSet set = statement.executeQuery("SELECT category FROM categories");
            while (set.next()) {
                list.add(set.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ObservableList<String> partsOfSpeech() {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            ResultSet set = statement.executeQuery("SELECT part_of_speech FROM parts");
            while (set.next()) {
                list.add(set.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public static boolean isLanguagePresent(String language) {
        boolean result = false;
        try {
            ResultSet set = statement.executeQuery("SELECT * FROM languages WHERE language = \""
                    + language + "\"");
            result = set.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isCategoryPresent(String category) {
        boolean result = false;
        try {
            ResultSet set = statement.executeQuery("SELECT * FROM categories WHERE category = \""
                    + category + "\"");
            result = set.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isWordPresent(String word, String language) {
        boolean result = false;
        try {
            ResultSet set = statement.executeQuery("SELECT * FROM main WHERE language = \""
                    + language + "\" AND word = \"" + word + "\"");
            result = set.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void insertLanguage(String language) {
        try {
            statement.executeUpdate("INSERT INTO languages VALUES (\"" + language + "\")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertCategory(String category) {
        try {
            statement.executeUpdate("INSERT INTO categories VALUES (\"" + category + "\")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertWord(String word, String translation, String language, String category, String partOfSpeech) {
        try {
            statement.executeUpdate(String.format("INSERT INTO main VALUES (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", 0.5, 0, \"%s\")",
                    word, translation, language, category, partOfSpeech, FORMAT.format(new Date())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
