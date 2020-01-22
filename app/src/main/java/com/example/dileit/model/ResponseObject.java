package com.example.dileit.model;

import com.google.gson.annotations.SerializedName;

public class ResponseObject {

    @SerializedName("response")
    private ResponseStatus mStatus;

    @SerializedName("meta")
    private ResponseMeta mMeta;

    @SerializedName("data")
    private ResponseData mData;

    public ResponseObject(ResponseStatus status, ResponseMeta meta, ResponseData data) {
        mStatus = status;
        mMeta = meta;
        mData = data;
    }

    public ResponseStatus getStatus() {
        return mStatus;
    }

    public ResponseMeta getMeta() {
        return mMeta;
    }

    public ResponseData getData() {
        return mData;
    }
}
