package com.example.dileit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseData {
    @SerializedName("num_found")
    private int listSize;

    @SerializedName("results")
    private List<WordSearch> mWordSearches;

    public ResponseData(int listSize, List<WordSearch> wordSearches) {
        this.listSize = listSize;
        mWordSearches = wordSearches;
    }

    public int getListSize() {
        return listSize;
    }

    public List<WordSearch> getWordSearches() {
        return mWordSearches;
    }
}
