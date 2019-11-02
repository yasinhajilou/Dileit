package com.example.dileit.model;

public class Word {
    private String engWord;
    private String perDefinition;

    public Word(String engWord, String perDefinition) {
        this.engWord = engWord;
        this.perDefinition = perDefinition;
    }

    public String getEngWord() {
        return engWord;
    }

    public String getPerDefinition() {
        return perDefinition;
    }
}
