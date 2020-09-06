package com.example.dileit.utils;

import android.content.Context;

import com.example.dileit.R;

public class StringUtils {
    static String TAG = StringUtils.class.getSimpleName();

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

    public static String[] dropDownItem(Context context){
        return new String[]{context.getString(R.string.verb), context.getString(R.string.noun), context.getString(R.string.adjective), context.getString(R.string.adverb), context.getString(R.string.phrase), context.getString(R.string.other)};

    }
}
