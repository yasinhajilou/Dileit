package com.yasinhajilou.dileit.model.database.persiandb;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class PersianDatabaseOpenHelper extends SQLiteAssetHelper {

    private final static String DB_NAME = "dictionary.db";
    private final static int DB_VERSION = 1;

     PersianDatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
}
