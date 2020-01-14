package com.example.dileit.model.database.engdb;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class EnglishDatabaseOpenHelper extends SQLiteAssetHelper {

    private final static int DB_VERSION = 1;
    private final static String DB_NAME = "engdic.db";

    public EnglishDatabaseOpenHelper(Context context) {
        super(context, DB_NAME , null, DB_VERSION);
    }
}
