package com.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class DataSource {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
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
            statement.executeUpdate(String.format(
                    "INSERT INTO main VALUES (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", 0.5, 0, 0, 0, \"%s\", NULL)",
                    word, translation, language, category, partOfSpeech, FORMAT.format(new Date())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Word> getWordsList(String language, String category, ReviseType reviseType) {
        ArrayList<Word> words = new ArrayList<>();
        String query = "SELECT * FROM main";
        String queryAdd = "";
        if (language.equals("All") && !category.equals("All")) {
            if (isCategoryPresent(category)) {
                queryAdd = " WHERE category = \"" + category + "\"";
            } else {
                queryAdd = " WHERE part_of_speech = \"" + category + "\"";
            }
        } else if (!language.equals("All") && category.equals("All")) {
            queryAdd = " WHERE language = \"" + language + "\"";
        } else if (!language.equals("All") && !category.equals("All")) {
            if (isCategoryPresent(category)) {
                queryAdd = " WHERE category = \"" + category +
                        "\" AND language = \"" + language + "\"";
            } else {
                queryAdd = " WHERE part_of_speech = \"" + category +
                        "\" AND language = \"" + language + "\"";
            }
        }
        if (reviseType.equals(ReviseType.NEW)) {
            if (queryAdd.equals("")) {
                queryAdd = " WHERE last_used IS NULL OR add_date >= \"" +
                        FORMAT.format(new Date(new Date().getTime() - 86400000)) + "\"";
            } else {
                queryAdd += " AND (last_used IS NULL OR add_date >= \"" +
                        FORMAT.format(new Date(new Date().getTime() - 86400000)) + "\")";
            }
        } else if (reviseType.equals(ReviseType.OLD)) {
            if (queryAdd.equals("")) {
                queryAdd = " WHERE last_used <= \"" +
                        FORMAT.format(new Date(new Date().getTime() - 864000000)) + "\"";
            } else {
                queryAdd += " AND (last_used <= \"" +
                        FORMAT.format(new Date(new Date().getTime() - 864000000)) + "\")";
            }
        } else if (reviseType.equals(ReviseType.WEAK)) {
            if (queryAdd.equals("")) {
                queryAdd = " WHERE rate <= 0.5";
            } else {
                queryAdd += " AND rate <= 0.5";
            }
        } else if (reviseType.equals(ReviseType.WRONGS)) {
            queryAdd += " ORDER BY wrongs DESC LIMIT 30";
        } else if (reviseType.equals(ReviseType.SHORT)) {
            queryAdd += " ORDER BY rate LIMIT 20";
        }
        query += queryAdd;
        try {
            ResultSet set = statement.executeQuery(query);
            while (set.next()) {
                words.add(new Word(set.getString(1), set.getString(2),
                        set.getString(3), set.getFloat(6), set.getInt(7),
                        set.getInt(8), set.getInt(9)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.shuffle(words);
        return words;
    }

    public static void update(Word word) {
        try {
            statement.executeUpdate(String.format(Locale.US, "UPDATE main SET rate = %f, uses = %d, " +
                            "corrects = %d, wrongs = %d, last_used = \"%s\" " +
                            "WHERE word = \"%s\" AND language = \"%s\"",
                    word.getRate(), word.getUses(), word.getCorrects(),
                    word.getWrongs(), word.getLastUsedDate(), word.getWord(), word.getLanguage()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
