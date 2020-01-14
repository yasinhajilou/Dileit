package com.example.dileit.model.repository;

import android.app.Application;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.dileit.model.WordEnglishDic;
import com.example.dileit.model.database.engdb.EnglishDatabaseAccess;
import com.example.dileit.viewmodel.EnglishDictionaryInterface;

import java.util.ArrayList;
import java.util.List;

public class EnglishDictionaryRepository {
    private EnglishDatabaseAccess mDatabaseAccess;
    private EnglishDictionaryInterface mInterface;

    public EnglishDictionaryRepository(Application application, EnglishDictionaryInterface anInterface) {
        mDatabaseAccess = EnglishDatabaseAccess.getINSTANCE(application);
        mInterface = anInterface;
    }


    public void getEngTrans(String word){
        new AsyncGetEngWord(mDatabaseAccess, mInterface , word).execute();
    }

    private class AsyncGetEngWord extends AsyncTask<Void, Void, List<WordEnglishDic>> {
        private EnglishDatabaseAccess mDatabaseAccess;
        private EnglishDictionaryInterface mInterface;
        private String word;

        public AsyncGetEngWord(EnglishDatabaseAccess databaseAccess, EnglishDictionaryInterface anInterface, String word) {
            mDatabaseAccess = databaseAccess;
            mInterface = anInterface;
            this.word = word;
        }

        @Override
        protected List<WordEnglishDic> doInBackground(Void... voids) {
            mDatabaseAccess.openDatabase();
            List<WordEnglishDic> wordEnglishDics = new ArrayList<>();

            Cursor cursor = mDatabaseAccess.getDatabase().rawQuery("SELECT word,type,defn FROM word WHERE word like ?", new String[]{word});
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String actualWord = cursor.getString(0);
                    String state = cursor.getString(1);
                    String def = cursor.getString(2);
                    WordEnglishDic englishDic = new WordEnglishDic(actualWord, state, def);
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
}
