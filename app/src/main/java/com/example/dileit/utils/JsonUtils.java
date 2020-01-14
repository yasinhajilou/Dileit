package com.example.dileit.utils;

import android.util.Log;

import com.example.dileit.model.WordInformation;
import com.google.gson.Gson;

public class JsonUtils {
    private String TAG = JsonUtils.class.getSimpleName();
    Gson mGson;

    public JsonUtils() {
        mGson = new Gson();
    }

    //Convert word data to wordDefinition class
    // wordDefinition : whole information of a word(idioms,means...)
    public WordInformation[] getWordDefinition(String data){
        WordInformation[] info = mGson.fromJson(data, WordInformation[].class);
        Log.d(TAG, "getWordDefinition: " + info.length);
        return info;
    }

}
