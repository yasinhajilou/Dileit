package com.example.dileit.model;

import com.google.gson.annotations.SerializedName;

public class IdiomExample {
    @SerializedName("e")
    private String example;

    @SerializedName("t")
    private String exampleTranslation;

    public IdiomExample(String example, String exampleTranslation) {
        this.example = example;
        this.exampleTranslation = exampleTranslation;
    }

    public String getExample() {
        return example;
    }

    public String getExampleTranslation() {
        return exampleTranslation;
    }
}
