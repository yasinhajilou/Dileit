package com.example.dileit.model.repository;

import android.app.Application;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;


import com.example.dileit.model.Word;
import com.example.dileit.model.database.DatabaseAccess;
import com.example.dileit.viewmodel.DictionaryInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DictionaryRepository {

    private static final String TAG = DictionaryRepository.class.getSimpleName();
    private DatabaseAccess databaseAccess;
    private DictionaryInterface mInterface;

    public DictionaryRepository(Application application, DictionaryInterface anInterface) {
        databaseAccess = DatabaseAccess.getINSTANCE(application);
        mInterface = anInterface;
    }

    public void getAllWords() {
        new AsyncGetAllWords(databaseAccess, mInterface).execute();
    }

    public void getSpecificWord(String word) {
        new AsyncGetSpecificWord(databaseAccess, mInterface, word).execute();
    }

    private class AsyncGetAllWords extends AsyncTask<Void, Void, List<Word>> {
        DatabaseAccess databaseAccess;
        DictionaryInterface mInterface;

         AsyncGetAllWords(DatabaseAccess databaseAccess, DictionaryInterface anInterface) {
            this.databaseAccess = databaseAccess;
            mInterface = anInterface;
        }

        @Override
        protected List<Word> doInBackground(Void... voids) {
            List<Word> wordsList = new ArrayList<>();
            databaseAccess.openDatabase();
            Cursor cursor = databaseAccess.getDatabase().rawQuery("SELECT word,def FROM dictionary LIMIT 50", null);
            while (cursor.moveToNext()) {
                String word = cursor.getString(0);
                String def = cursor.getString(1);
                Word dictionary = new Word(word, def);
                wordsList.add(dictionary);
            }
            Log.d(TAG, String.valueOf(wordsList.size()));
            cursor.close();
            databaseAccess.closeDatabase();

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
        DatabaseAccess databaseAccess;
        DictionaryInterface mInterface;

         AsyncGetSpecificWord(DatabaseAccess databaseAccess, DictionaryInterface anInterface, String word) {
            this.databaseAccess = databaseAccess;
            mInterface = anInterface;
            this.word = word;
        }

        @Override
        protected List<Word> doInBackground(Void... voids) {
             // ORDER BY word COLLATE NOCASE ASC
            databaseAccess.openDatabase();
            List<Word> wordsList = new ArrayList<>();
            Cursor cursor = databaseAccess.getDatabase().rawQuery("SELECT word,def from dictionary WHERE word LIKE ? ORDER BY LOWER(word) LIMIT 50 ", new String[]{word+"%"});
            while (cursor.moveToNext()) {
                String actualWord = cursor.getString(0);
                String def = cursor.getString(1);
                Word word = new Word(actualWord , def);
                wordsList.add(word);
            }
            Log.d(TAG, String.valueOf(wordsList.size()));
            cursor.close();
            databaseAccess.closeDatabase();

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
