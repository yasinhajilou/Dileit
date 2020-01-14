package com.example.dileit.model.database.persiandb;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class PersianDatabaseOpenHelper extends SQLiteAssetHelper {

    public final static String DICTIONARY_ENG_TO_PERSION_TABLE_NAME = "dictionary";
    private final static String DB_NAME = "dictionary.db";
    private final static int DB_VERSION = 1;

    public PersianDatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
}
