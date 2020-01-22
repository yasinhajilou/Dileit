package com.example.dileit.model;

import com.google.gson.annotations.SerializedName;

public class ResponseMeta {

    @SerializedName("q")
    private String query;
    @SerializedName("type")
    private String type;
    @SerializedName("filter")
    private String filter;

    public ResponseMeta(String query, String type, String filter) {
        this.query = query;
        this.type = type;
        this.filter = filter;
    }


    public String getQuery() {
        return query;
    }

    public String getType() {
        return type;
    }

    public String getFilter() {
        return filter;
    }
}
