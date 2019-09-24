package com.example.dileit.model;

public class Dictionary {
    private String engWord;
    private String perDefinition;

    public Dictionary(String engWord, String perDefinition) {
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
