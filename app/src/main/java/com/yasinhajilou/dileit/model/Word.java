package com.yasinhajilou.dileit.model;

public class Word {
    private String word;
    private String definition;
    public Word(String word, String definition) {
        this.word = word;
        this.definition = definition;
    }

    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }
}
