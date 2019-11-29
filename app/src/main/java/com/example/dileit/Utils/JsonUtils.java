package com.example.dileit.Utils;

import com.example.dileit.model.WordDefinition;
import com.google.gson.Gson;

public class JsonUtils {
    Gson mGson;

    public JsonUtils() {
        mGson = new Gson();
    }

    //Convert word data to wordDefinition class
    // wordDefinition : whole information of a word(idioms,means...)
    public WordDefinition getWordDefinition(String data){
        WordDefinition[] info = mGson.fromJson(data, WordDefinition[].class);
        return info[0];
    }

}
