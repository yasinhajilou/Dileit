package com.example.dileit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TranslationWord {

    @SerializedName("s")
    private String translatedWord;
    @SerializedName("es")
    private List<TranslationExample> mTranslationExamples;

    public TranslationWord(String translatedWord, List<TranslationExample> translationExamples) {
        this.translatedWord = translatedWord;
        this.mTranslationExamples = translationExamples;
    }

    public String getTranslatedWord() {
        return translatedWord;
    }

    public List<TranslationExample> getTranslationExamples() {
        return mTranslationExamples;
    }

    @Override
    public String toString() {
        return "TranslationWord{" +
                "translatedWord='" + translatedWord + '\'' +
                ", mTranslationExamples=" + mTranslationExamples  +
                '}';
    }
}
