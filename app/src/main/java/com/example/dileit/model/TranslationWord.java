package com.example.dileit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TranslationWord {

    @SerializedName("s")
    private String translatedWord;
    @SerializedName("es")
    private List<Example> examples;

    public TranslationWord(String translatedWord, List<Example> examples) {
        this.translatedWord = translatedWord;
        this.examples = examples;
    }

    public String getTranslatedWord() {
        return translatedWord;
    }

    public List<Example> getExamples() {
        return examples;
    }
}
