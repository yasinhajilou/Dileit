package com.example.dileit.model.repository;

import android.app.Application;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.dileit.model.SearchDictionary;
import com.example.dileit.model.EnglishDef;
import com.example.dileit.model.database.engdb.EnglishDatabaseAccess;
import com.example.dileit.viewmodel.vminterface.EnglishDictionaryInterface;

import java.util.ArrayList;
import java.util.List;

public class EnglishDictionaryRepository {
    private EnglishDatabaseAccess mDatabaseAccess;
    private EnglishDictionaryInterface mInterface;

    public EnglishDictionaryRepository(Application application, EnglishDictionaryInterface anInterface) {
        mDatabaseAccess = EnglishDatabaseAccess.getINSTANCE(application);
        mInterface = anInterface;
    }


    //search in keys table for finding typed word
    public void doSearch(String word) {
        new AsyncGetEngWord(mDatabaseAccess, mInterface, word).execute();
    }

    public void getRefById(int engId) {
        new AsyncGetDefByRef(mDatabaseAccess , mInterface , engId).execute();
    }

    private class AsyncGetEngWord extends AsyncTask<Void, Void, List<SearchDictionary>> {
        private EnglishDatabaseAccess mDatabaseAccess;
        private EnglishDictionaryInterface mInterface;
        private String word;

        AsyncGetEngWord(EnglishDatabaseAccess databaseAccess, EnglishDictionaryInterface anInterface, String word) {
            mDatabaseAccess = databaseAccess;
            mInterface = anInterface;
            this.word = word;
        }

        @Override
        protected List<SearchDictionary> doInBackground(Void... voids) {
            mDatabaseAccess.openDatabase();
            List<SearchDictionary> wordEnglishDics = new ArrayList<>();

            Cursor cursor = mDatabaseAccess.getDatabase().rawQuery("SELECT _idref,word FROM keys WHERE word like ?", new String[]{word + "%"});
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int refrenceId = cursor.getInt(0);
                    String actualWord = cursor.getString(1).trim();
                    SearchDictionary englishDic = new SearchDictionary(actualWord, refrenceId);
                    wordEnglishDics.add(englishDic);
                }
            }

            mInterface.getEngWord(wordEnglishDics);
            mDatabaseAccess.closeDatabase();

            if (cursor != null) {
                cursor.close();
            }

            return wordEnglishDics;
        }
    }

    private class AsyncGetDefByRef extends AsyncTask<Void, Void, Void> {

        private EnglishDatabaseAccess mDatabaseAccess;
        private EnglishDictionaryInterface mInterface;
        private int id;

        AsyncGetDefByRef(EnglishDatabaseAccess databaseAccess, EnglishDictionaryInterface anInterface, int id) {
            mDatabaseAccess = databaseAccess;
            mInterface = anInterface;
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDatabaseAccess.openDatabase();
            List<EnglishDef> englishDefs = new ArrayList<>();

            Cursor cursor = mDatabaseAccess.getDatabase().rawQuery("SELECT definition,category,synonyms,examples FROM description WHERE _id like ?", new String[]{String.valueOf(id)});
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String def  = cursor.getString(0);
                    String cat = cursor.getString(1);
                    String syn = cursor.getString(2);
                    String exam = cursor.getString(3);
                    EnglishDef englishDic = new EnglishDef(cat,def,syn,exam);
                    englishDefs.add(englishDic);
                }
            }

            mInterface.getDefById(englishDefs);
            mDatabaseAccess.closeDatabase();

            if (cursor != null) {
                cursor.close();
            }

            return null;
        }
    }
}
