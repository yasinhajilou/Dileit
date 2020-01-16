package com.example.dileit.model;

import com.google.gson.annotations.SerializedName;

public class SearchResponse {

    @SerializedName("data")
    private ListData mListData;

    public SearchResponse(ListData listData) {
        mListData = listData;
    }

    public ListData getListData() {
        return mListData;
    }
}
