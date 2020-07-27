package com.example.dileit.model.database.persiandb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PersianDatabaseAccess {
    private SQLiteOpenHelper mHelper;
    private SQLiteDatabase mDatabase;
    private static PersianDatabaseAccess INSTANCE;

    private PersianDatabaseAccess(Context context) {
        this.mHelper = new PersianDatabaseOpenHelper(context);
    }

    public static PersianDatabaseAccess getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (PersianDatabaseAccess.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PersianDatabaseAccess(context);
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

    @Override
    protected void finalize() throws Throwable {
        if (null != mHelper)
            mHelper.close();
        if (null != mDatabase)
            mDatabase.close();
        super.finalize();
    }
}
