package com.example.dileit.model.database.engdb;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

 class EnglishDatabaseOpenHelper extends SQLiteAssetHelper {

    private final static int DB_VERSION = 1;
    private final static String DB_NAME = "eng2engdic.db";

     EnglishDatabaseOpenHelper(Context context) {
        super(context, DB_NAME , null, DB_VERSION);
    }
}
