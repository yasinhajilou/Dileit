package com.yasinhajilou.dileit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IdiomInformation {
    @SerializedName("s")
    private String idiomTranslation;

    @SerializedName("es")
    private List<IdiomExample> idiomExamples;


    public IdiomInformation(String idiomTranslation, List<IdiomExample> idiomExamples) {
        this.idiomTranslation = idiomTranslation;
        this.idiomExamples = idiomExamples;
    }

    public String getIdiomTranslation() {
        return idiomTranslation;
    }

    public List<IdiomExample> getIdiomExamples() {
        return idiomExamples;
    }
}
