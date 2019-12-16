package com.example.dileit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IdiomInformation {
    @SerializedName("s")
    private String idomTranslation;

    @SerializedName("es")
    private List<IdiomExample> idiomExamples;


    public IdiomInformation(String idomTranslation, List<IdiomExample> idiomExamples) {
        this.idomTranslation = idomTranslation;
        this.idiomExamples = idiomExamples;
    }

    public String getIdomTranslation() {
        return idomTranslation;
    }

    public List<IdiomExample> getIdiomExamples() {
        return idiomExamples;
    }
}
