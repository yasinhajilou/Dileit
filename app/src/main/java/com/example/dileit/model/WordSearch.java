package com.example.dileit.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class WordSearch {

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String persianTitle;

    @SerializedName("title_en")
    private String englishTitle;

    @SerializedName("text")
    private String translation;

    @SerializedName("source")
    private String source;

    @SerializedName("db")
    private String db;

    @SerializedName("num")
    private String num;

    public WordSearch(String id, String persianTitle, String englishTitle, String translation, String source, String db, String num) {
        this.id = id;
        this.persianTitle = persianTitle;
        this.englishTitle = englishTitle;
        this.translation = translation;
        this.source = source;
        this.db = db;
        this.num = num;
    }


    public String getId() {
        return id;
    }

    public String getPersianTitle() {
        return persianTitle;
    }

    public String getEnglishTitle() {
        return englishTitle;
    }

    public String getTranslation() {
        return translation;
    }

    public String getSource() {
        return source;
    }

    public String getDb() {
        return db;
    }

    public String getNum() {
        return num;
    }

    @NonNull
    @Override
    public String toString() {
        return getEnglishTitle()+"\n"+
                getTranslation()+"\n";
    }
}
