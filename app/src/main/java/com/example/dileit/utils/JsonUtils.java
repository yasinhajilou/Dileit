package com.example.dileit.utils;

import android.util.Log;

import com.example.dileit.model.Word;
import com.example.dileit.model.WordInformation;
import com.google.gson.Gson;

public class JsonUtils {
    private String TAG = JsonUtils.class.getSimpleName();
    private Gson mGson;

    public JsonUtils() {
        mGson = new Gson();
    }

    //Convert word data to wordDefinition class
    // wordDefinition : whole information of a word(idioms,means...)
    public WordInformation[] getWordDefinition(String data) {
        WordInformation[] info = mGson.fromJson(data, WordInformation[].class);
        return info;
    }


    //convert Json data to translated content
    public StringBuilder getTranslation(WordInformation[] data, StringBuilder stringBuilder) {
        if (data != null) {
            for (int i = 0; i < data[0].getTranslationWords().size(); i++) {
                stringBuilder.append(data[0].getTranslationWords().get(i).getTranslatedWord()).append(".");
            }
        }
        return stringBuilder;
    }
}
