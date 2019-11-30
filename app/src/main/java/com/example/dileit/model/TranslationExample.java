package com.example.dileit.model;

import com.google.gson.annotations.SerializedName;

public class TranslationExample {
    @SerializedName("e")
    private String sentence;
    @SerializedName("t")
    private String translation;

    public TranslationExample(String sentence, String translation) {
        this.sentence = sentence;
        this.translation = translation;
    }

    public String getSentence() {
        return sentence;
    }

    public String getTranslation() {
        return translation;
    }

    @Override
    public String toString() {
        return "TranslationExample{" +
                "sentence='" + sentence + '\'' +
                ", translation='" + translation + '\'' +
                '}';
    }
}
