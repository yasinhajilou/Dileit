package com.example.dileit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Idiom {

    @SerializedName("i")
    private String title;

    @SerializedName("ss")
    private List<IdiomInformation> idiomInformation;

    public Idiom(String title, List<IdiomInformation> idiomInformation) {
        this.title = title;
        this.idiomInformation = idiomInformation;
    }

    public String getTitle() {
        return title;
    }

    public List<IdiomInformation> getIdiomInformation() {
        return idiomInformation;
    }
}
