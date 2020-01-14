package com.example.dileit.model;

public class WordEnglishDic {
    private String word;
    private String state;
    private String definition;

    public WordEnglishDic(String word, String state, String definition) {
        this.word = word;
        this.state = state;
        this.definition = definition;
    }

    public String getWord() {
        return word;
    }

    public String getState() {
        return state;
    }

    public String getDefinition() {
        return definition;
    }
}
