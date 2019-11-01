package com.example.dileit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TranslatedInfo {
    @SerializedName("g")
    private String type;
    @SerializedName("ss")
    private List<Object> translationWords;
    @SerializedName("p")
    private String pronunciation;
    @SerializedName("is")
    private List<Object> idioms;


    public TranslatedInfo(String type, List<Object> translationWords, String pronunciation, List<Object> idioms) {
        this.type = type;
        this.translationWords = translationWords;
        this.pronunciation = pronunciation;
        this.idioms = idioms;
    }

    public String getType() {
        return type;
    }

    public List<Object> getTranslationWords() {
        return translationWords;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public List<Object> getIdioms() {
        return idioms;
    }
}
