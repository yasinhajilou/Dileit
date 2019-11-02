package com.example.dileit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TranslationWord {
    @SerializedName("s")
    private String translatedWord;
    @SerializedName("es")
    private List<Example> examples;
}
