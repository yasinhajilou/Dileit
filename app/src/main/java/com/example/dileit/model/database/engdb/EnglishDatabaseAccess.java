package com.example.dileit.model.database.engdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EnglishDatabaseAccess {
    private SQLiteOpenHelper mHelper;
    private SQLiteDatabase mDatabase;
    private static EnglishDatabaseAccess INSTANCE;

    private EnglishDatabaseAccess(Context context) {
        mHelper = new EnglishDatabaseOpenHelper(context);
    }

    public static EnglishDatabaseAccess getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (EnglishDatabaseAccess.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EnglishDatabaseAccess(context);
                }
            }
        }
        return INSTANCE;
    }

    public void openDatabase() {
        this.mDatabase = mHelper.getReadableDatabase();
    }

    public SQLiteDatabase getDatabase() {
        return mDatabase;
    }

    public void closeDatabase() {
        if (this.mDatabase != null)
            mHelper.close();
    }
}
