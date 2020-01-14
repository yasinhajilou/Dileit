package com.example.dileit.model.repository;

import android.app.Application;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;


import com.example.dileit.model.Word;
import com.example.dileit.model.database.persiandb.PersianDatabaseAccess;
import com.example.dileit.viewmodel.DictionaryInterface;
import java.util.ArrayList;
import java.util.List;

public class PersianDictionaryRepository {

    private static final String TAG = PersianDictionaryRepository.class.getSimpleName();
    private PersianDatabaseAccess mPersianDatabaseAccess;
    private DictionaryInterface mInterface;

    public PersianDictionaryRepository(Application application, DictionaryInterface anInterface) {
        mPersianDatabaseAccess = PersianDatabaseAccess.getINSTANCE(application);
        mInterface = anInterface;
    }

    public void getAllWords() {
        new AsyncGetAllWords(mPersianDatabaseAccess, mInterface).execute();
    }

    public void getSpecificWord(String word) {
        new AsyncGetSpecificWord(mPersianDatabaseAccess, mInterface, word).execute();
    }

    private class AsyncGetAllWords extends AsyncTask<Void, Void, List<Word>> {
        PersianDatabaseAccess mPersianDatabaseAccess;
        DictionaryInterface mInterface;

         AsyncGetAllWords(PersianDatabaseAccess persianDatabaseAccess, DictionaryInterface anInterface) {
            this.mPersianDatabaseAccess = persianDatabaseAccess;
            mInterface = anInterface;
        }

        @Override
        protected List<Word> doInBackground(Void... voids) {
            List<Word> wordsList = new ArrayList<>();
            mPersianDatabaseAccess.openDatabase();
            Cursor cursor = mPersianDatabaseAccess.getDatabase().rawQuery("SELECT word,def FROM dictionary LIMIT 50", null);
            while (cursor.moveToNext()) {
                String word = cursor.getString(0);
                String def = cursor.getString(1);
                Word dictionary = new Word(word, def);
                wordsList.add(dictionary);
            }
            cursor.close();
            mPersianDatabaseAccess.closeDatabase();

            return wordsList;
        }

        @Override
        protected void onPostExecute(List<Word> word) {
            super.onPostExecute(word);
            mInterface.allEngWords(word);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    private class AsyncGetSpecificWord extends AsyncTask<Void, Void, List<Word>> {
        String word;
        PersianDatabaseAccess mPersianDatabaseAccess;
        DictionaryInterface mInterface;

         AsyncGetSpecificWord(PersianDatabaseAccess persianDatabaseAccess, DictionaryInterface anInterface, String word) {
            this.mPersianDatabaseAccess = persianDatabaseAccess;
            mInterface = anInterface;
            this.word = word;
        }

        @Override
        protected List<Word> doInBackground(Void... voids) {
             // ORDER BY word COLLATE NOCASE ASC
            mPersianDatabaseAccess.openDatabase();
            List<Word> wordsList = new ArrayList<>();
            Cursor cursor = mPersianDatabaseAccess.getDatabase().rawQuery("SELECT word,def from dictionary WHERE word LIKE ? ORDER BY LOWER(word) LIMIT 50 ", new String[]{word+"%"});
            while (cursor.moveToNext()) {
                String actualWord = cursor.getString(0);
                String def = cursor.getString(1);
                Word word = new Word(actualWord , def);
                wordsList.add(word);
            }
            Log.d(TAG, String.valueOf(wordsList.size()));
            cursor.close();
            mPersianDatabaseAccess.closeDatabase();

            return wordsList;
        }


        @Override
        protected void onPostExecute(List<Word> word) {
            super.onPostExecute(word);
            mInterface.allEngToPer(word);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }



}
