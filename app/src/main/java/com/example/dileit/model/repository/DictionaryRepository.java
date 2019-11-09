package com.example.dileit.model.repository;

import android.app.Application;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;


import com.example.dileit.model.Word;
import com.example.dileit.model.WordDefinition;
import com.example.dileit.model.database.DatabaseAccess;
import com.example.dileit.viewmodel.DictionaryInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DictionaryRepository {

    private static final String TAG = DictionaryRepository.class.getSimpleName() + "debugging";
    private DatabaseAccess databaseAccess;
    private DictionaryInterface mInterface;

    public DictionaryRepository(Application application, DictionaryInterface anInterface) {
        databaseAccess = DatabaseAccess.getINSTANCE(application);
        mInterface = anInterface;
    }

    public void getAllEngWords() {
        new AsyncGetAllEngWords(databaseAccess, mInterface).execute();
    }

    public void getEngToPer(String word) {
        new AsyncGetEngToPer(databaseAccess, mInterface, word).execute();
    }

    private class AsyncGetAllEngWords extends AsyncTask<Void, Void, List<Word>> {
        DatabaseAccess databaseAccess;
        DictionaryInterface mInterface;

         AsyncGetAllEngWords(DatabaseAccess databaseAccess, DictionaryInterface anInterface) {
            this.databaseAccess = databaseAccess;
            mInterface = anInterface;
        }

        @Override
        protected List<Word> doInBackground(Void... voids) {
            List<Word> wordsList = new ArrayList<>();
            Cursor cursor = databaseAccess.getDatabase().rawQuery("SELECT word,def FROM dictionary LIMIT 500", new String[]{});
            while (cursor.moveToNext()) {
                String word = cursor.getString(0);
                String def = cursor.getString(1);
                Word dictionary = new Word(word, def);
                wordsList.add(dictionary);
            }
            Log.d(TAG, String.valueOf(wordsList.size()));
            cursor.close();
            return wordsList;
        }

        @Override
        protected void onPostExecute(List<Word> word) {
            super.onPostExecute(word);
            mInterface.allEngWords(word);
            databaseAccess.closeDatabase();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            databaseAccess.openDatabase();
        }
    }

    private class AsyncGetEngToPer extends AsyncTask<Void, Void, List<Word>> {
        String word;
        DatabaseAccess databaseAccess;
        DictionaryInterface mInterface;

         AsyncGetEngToPer(DatabaseAccess databaseAccess, DictionaryInterface anInterface, String word) {
            this.databaseAccess = databaseAccess;
            mInterface = anInterface;
            this.word = word;
        }

        @Override
        protected List<Word> doInBackground(Void... voids) {
            databaseAccess.openDatabase();
            List<Word> wordsList = new ArrayList<>();
            Cursor cursor = databaseAccess.getDatabase().rawQuery("SELECT word,def FROM dictionary WHERE word like 'سلام %' ORDER BY word DESC  LIMIT 200  " , null);
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
