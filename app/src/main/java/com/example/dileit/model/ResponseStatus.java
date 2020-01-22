package com.example.dileit.model;

import com.google.gson.annotations.SerializedName;

public class ResponseStatus {
    @SerializedName("status")
    boolean status;
    @SerializedName("code")
    int code;
    @SerializedName("state")
    String state;

    public ResponseStatus(boolean status, int code, String state) {
        this.status = status;
        this.code = code;
        this.state = state;
    }

    public boolean isStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getState() {
        return state;
    }
}
