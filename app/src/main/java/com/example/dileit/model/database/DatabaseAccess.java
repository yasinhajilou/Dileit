package com.example.dileit.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess {
    private SQLiteOpenHelper mHelper;
    private SQLiteDatabase mDatabase;
    private static DatabaseAccess INSTANCE;

    private DatabaseAccess(Context context) {
        this.mHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseAccess.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DatabaseAccess(context);
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
