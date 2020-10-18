package com.yasinhajilou.dileit.utils;


import com.yasinhajilou.dileit.model.WordInformation;
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
        if (!data.equals("Error"))
            return mGson.fromJson(data, WordInformation[].class);
        else
            return null;

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
