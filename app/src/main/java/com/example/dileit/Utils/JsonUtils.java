package com.example.dileit.Utils;

import com.example.dileit.model.WordInformation;
import com.google.gson.Gson;

public class JsonUtils {
    Gson mGson;

    public JsonUtils() {
        mGson = new Gson();
    }

    //Convert word data to wordDefinition class
    // wordDefinition : whole information of a word(idioms,means...)
    public WordInformation getWordDefinition(String data){
        WordInformation[] info = mGson.fromJson(data, WordInformation[].class);
        return info[0];
    }

}
