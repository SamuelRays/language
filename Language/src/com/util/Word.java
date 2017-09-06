package com.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Word {
    private String word;
    private String translation;
    private String userTranslation;
    private String language;
    private double rate;
    private int uses;
    private int corrects;
    private int wrongs;
    private String lastUsedDate = "NULL";

    public Word(String word, String translation, String language, double rate, int uses, int corrects, int wrongs) {
        this.word = word;
        this.translation = translation;
        this.language = language;
        this.rate = rate;
        this.uses = uses;
        this.corrects = corrects;
        this.wrongs = wrongs;
    }

    public void changeRate(boolean positive) {
        if (positive) {
            rate = 0.9 * rate + 0.1;
        } else {
            rate = 0.9 * rate;
        }
    }

    public void setUsedDate() {
        lastUsedDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }

    public String getUserTranslation() {
        return userTranslation;
    }

    public void setUserTranslation(String userTranslation) {
        this.userTranslation = userTranslation;
    }

    public String getLanguage() {
        return language;
    }

    public double getRate() {
        return rate;
    }

    public int getUses() {
        return uses;
    }

    public void incrementUses() {
        uses++;
    }

    public int getCorrects() {
        return corrects;
    }

    public void incrementCorrects() {
        corrects++;
    }

    public int getWrongs() {
        return wrongs;
    }

    public void incrementWrongs() {
        wrongs++;
    }

    public String getLastUsedDate() {
        return lastUsedDate;
    }
}
