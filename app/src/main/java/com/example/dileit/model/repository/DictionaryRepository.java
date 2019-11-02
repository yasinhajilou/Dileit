package com.example.dileit.model.repository;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;


import com.example.dileit.model.Dictionary;
import com.example.dileit.model.database.DatabaseAccess;
import com.example.dileit.model.database.DatabaseOpenHelper;
import com.example.dileit.viewmodel.DictionaryInterface;

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

    private class AsyncGetAllEngWords extends AsyncTask<Void, Void, List<Dictionary>> {
        DatabaseAccess databaseAccess;
        DictionaryInterface mInterface;

         AsyncGetAllEngWords(DatabaseAccess databaseAccess, DictionaryInterface anInterface) {
            this.databaseAccess = databaseAccess;
            mInterface = anInterface;
        }

        @Override
        protected List<Dictionary> doInBackground(Void... voids) {
            List<Dictionary> wordsList = new ArrayList<>();
            Cursor cursor = databaseAccess.getDatabase().rawQuery("SELECT word,def FROM dictionary LIMIT 500", new String[]{});
            while (cursor.moveToNext()) {
                String word = cursor.getString(0);
                String def = cursor.getString(1);
                Dictionary dictionary = new Dictionary(word, def);
                wordsList.add(dictionary);
            }
            Log.d(TAG, String.valueOf(wordsList.size()));
            cursor.close();
            return wordsList;
        }

        @Override
        protected void onPostExecute(List<Dictionary> dictionary) {
            super.onPostExecute(dictionary);
            mInterface.allEngWords(dictionary);
            databaseAccess.closeDatabase();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            databaseAccess.openDatabase();
        }
    }

    private class AsyncGetEngToPer extends AsyncTask<Void, Void, List<Dictionary>> {
        String word;
        DatabaseAccess databaseAccess;
        DictionaryInterface mInterface;

         AsyncGetEngToPer(DatabaseAccess databaseAccess, DictionaryInterface anInterface, String word) {
            this.databaseAccess = databaseAccess;
            mInterface = anInterface;
            this.word = word;
        }

        @Override
        protected List<Dictionary> doInBackground(Void... voids) {
            databaseAccess.openDatabase();
            List<Dictionary> wordsList = new ArrayList<>();

//
//            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
//            queryBuilder.setTables(DatabaseOpenHelper.DICTIONARY_ENG_TO_PERSION_TABLE_NAME);
//
//            String selection = "word" + " MATCH ?";
//            Cursor cursor = queryBuilder.query(databaseAccess.getDatabase() , new String[]{"word" , "def"} ,
//                   selection , new String[]{word+"*"} , null , null , null );

            Cursor cursor = databaseAccess.getDatabase().rawQuery("SELECT word,def FROM dictionary WHERE word LIKE ?  LIMIT 20", new String[]{word+"%"});
            while (cursor.moveToNext()) {
                String word = cursor.getString(0);
                String def = cursor.getString(1);
                Dictionary dictionary = new Dictionary(word, def);
                wordsList.add(dictionary);
            }
            Log.d(TAG, String.valueOf(wordsList.size()));
            cursor.close();
            databaseAccess.closeDatabase();
            return wordsList;
        }


        @Override
        protected void onPostExecute(List<Dictionary> dictionary) {
            super.onPostExecute(dictionary);
            mInterface.allEngToPer(dictionary);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}
