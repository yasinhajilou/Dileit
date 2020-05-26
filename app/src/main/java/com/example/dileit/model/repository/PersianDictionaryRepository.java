package com.example.dileit.model.repository;

import android.app.Application;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;


import com.example.dileit.model.SearchDictionary;
import com.example.dileit.model.Word;
import com.example.dileit.model.WordInformation;
import com.example.dileit.model.database.persiandb.PersianDatabaseAccess;
import com.example.dileit.viewmodel.vminterface.PersionDictionaryInterface;
import java.util.ArrayList;
import java.util.List;

public class PersianDictionaryRepository {

    private static final String TAG = PersianDictionaryRepository.class.getSimpleName();
    private PersianDatabaseAccess mPersianDatabaseAccess;
    private PersionDictionaryInterface mInterface;

    public PersianDictionaryRepository(Application application, PersionDictionaryInterface anInterface) {
        mPersianDatabaseAccess = PersianDatabaseAccess.getINSTANCE(application);
        mInterface = anInterface;
    }

    public void getAllWords() {
        new AsyncGetAllWords(mPersianDatabaseAccess, mInterface).execute();
    }

    public void getSpecificWord(String word) {
        new AsyncGetSpecificWordBySearch(mPersianDatabaseAccess, mInterface, word).execute();
    }

    public void getExactWord(String word){
        new AsyncGetExactWord(mPersianDatabaseAccess , mInterface , word).execute();
    }

    private class AsyncGetAllWords extends AsyncTask<Void, Void, List<Word>> {
        PersianDatabaseAccess mPersianDatabaseAccess;
        PersionDictionaryInterface mInterface;

         AsyncGetAllWords(PersianDatabaseAccess persianDatabaseAccess, PersionDictionaryInterface anInterface) {
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
            mInterface.allWords(word);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    private class AsyncGetSpecificWordBySearch extends AsyncTask<Void, Void, List<SearchDictionary>> {
        String word;
        PersianDatabaseAccess mPersianDatabaseAccess;
        PersionDictionaryInterface mInterface;

         AsyncGetSpecificWordBySearch(PersianDatabaseAccess persianDatabaseAccess, PersionDictionaryInterface anInterface, String word) {
            this.mPersianDatabaseAccess = persianDatabaseAccess;
            mInterface = anInterface;
            this.word = word;
        }

        @Override
        protected List<SearchDictionary> doInBackground(Void... voids) {
             // ORDER BY word COLLATE NOCASE ASC
            mPersianDatabaseAccess.openDatabase();
            List<SearchDictionary> wordsList = new ArrayList<>();
            Cursor cursor = mPersianDatabaseAccess.getDatabase().rawQuery("SELECT word from dictionary WHERE word LIKE ? ORDER BY LOWER(word) LIMIT 50 ", new String[]{word+"%"});
            while (cursor.moveToNext()) {
                String actualWord = cursor.getString(0).trim();
                SearchDictionary searchDictionary = new SearchDictionary(actualWord , 0);
                wordsList.add(searchDictionary);
            }
            cursor.close();
            mPersianDatabaseAccess.closeDatabase();
            mInterface.getSpecificWord(wordsList);

            return wordsList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    private class AsyncGetExactWord extends AsyncTask<Void, Void, Void> {
        String word;
        PersianDatabaseAccess mPersianDatabaseAccess;
        PersionDictionaryInterface mInterface;

        AsyncGetExactWord(PersianDatabaseAccess persianDatabaseAccess, PersionDictionaryInterface anInterface, String word) {
            this.mPersianDatabaseAccess = persianDatabaseAccess;
            mInterface = anInterface;
            this.word = word;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // ORDER BY word COLLATE NOCASE ASC
            Log.d(TAG, "doInBackground: " + word);

            mPersianDatabaseAccess.openDatabase();
            Cursor cursor = mPersianDatabaseAccess.getDatabase().rawQuery("SELECT def from dictionary WHERE word LIKE ?  ", new String[]{word});
            String data = null;
            while (cursor.moveToNext()) {
                data = cursor.getString(0);
            }
            cursor.close();
            mPersianDatabaseAccess.closeDatabase();
            Log.d(TAG, "doInBackground: " + data);
            mInterface.getExactWordData(data);

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }


}
