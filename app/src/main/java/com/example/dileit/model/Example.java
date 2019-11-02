package com.example.dileit.model;

import com.google.gson.annotations.SerializedName;

public class Example {
    @SerializedName("e")
    private String title;
    @SerializedName("t")
    private String translation;

    public Example(String title, String translation) {
        this.title = title;
        this.translation = translation;
    }

    public String getTitle() {
        return title;
    }

    public String getTranslation() {
        return translation;
    }
}
