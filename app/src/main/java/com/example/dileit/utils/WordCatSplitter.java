package com.example.dileit.utils;

import android.util.Log;

public class WordCatSplitter {
    static String TAG = WordCatSplitter.class.getSimpleName();

    public static String[] catSplitter(String string) {
        return string.trim().split("\\W");
    }

    public static String decoratedString(String string) {
        if (string.equals(""))
            return "";

        StringBuilder builder = new StringBuilder();
        String splitter = " / ";
        int a = 0;
        for (String s :
                catSplitter(string)) {
            if (s.equals(""))
                continue;
            if (a > 0)
                builder.append(splitter);
            a++;
            switch (s) {
                case "vt":
                    s = "Verb-transitive-";
                    break;
                case "vi":
                    s = "Verb-intransitive-";
                    break;
                case "n":
                    s = "Noun";
                    break;
                case "adj":
                    s = "Adjective";
                    break;
                case "adv":
                    s = "Adverb";
                    break;
                case "interj":
                    s = "Interjection";
                    break;
                case "pl":
                    s = "Plural";
                    break;
            }
            builder.append(s);
        }
        return builder.toString();
    }
}
