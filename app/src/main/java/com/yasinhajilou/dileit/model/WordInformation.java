package com.yasinhajilou.dileit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WordInformation {
    @SerializedName("g")
    private String type;
    @SerializedName("ss")
    private List<TranslationWord> translationWords;
    @SerializedName("p")
    private String pronunciation;
    @SerializedName("is")
    private List<Idiom> idioms;


    public WordInformation(String type, List<TranslationWord> translationWords, String pronunciation, List<Idiom> idioms) {
        this.type = type;
        this.translationWords = translationWords;
        this.pronunciation = pronunciation;
        this.idioms = idioms;
    }

    public String getType() {
        return type;
    }

    public List<TranslationWord> getTranslationWords() {
        return translationWords;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public List<Idiom> getIdioms() {
        return idioms;
    }
}
