package com.example.dileit.model.repository;

import android.app.Application;
import android.database.Cursor;


import com.example.dileit.model.SearchDictionary;
import com.example.dileit.model.Word;
import com.example.dileit.model.database.persiandb.PersianDatabaseAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class PersianDictionaryRepository {

    private static final String TAG = PersianDictionaryRepository.class.getSimpleName();
    private PersianDatabaseAccess mPersianDatabaseAccess;

    public PersianDictionaryRepository(Application application) {
        mPersianDatabaseAccess = PersianDatabaseAccess.getINSTANCE(application);
    }

    public Flowable<List<SearchDictionary>> searchWords(String word) {
        return Flowable.fromCallable(() -> {
            // ORDER BY word COLLATE NOCASE ASC
            mPersianDatabaseAccess.openDatabase();
            List<SearchDictionary> wordsList = new ArrayList<>();
            Cursor cursor = mPersianDatabaseAccess.getDatabase().rawQuery("SELECT word from dictionary WHERE word LIKE ? ORDER BY LOWER(word) LIMIT 50 ", new String[]{word + "%"});
            while (cursor.moveToNext()) {
                String actualWord = cursor.getString(0).trim();
                SearchDictionary searchDictionary = new SearchDictionary(actualWord, 0);
                wordsList.add(searchDictionary);
            }
            cursor.close();
            mPersianDatabaseAccess.closeDatabase();
            return wordsList;
        }).subscribeOn(Schedulers.io());
    }

    public Flowable<String> getExactWord(String word) {
        return Flowable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                mPersianDatabaseAccess.openDatabase();
                Cursor cursor = mPersianDatabaseAccess.getDatabase().rawQuery("SELECT def from dictionary WHERE word LIKE ?  ", new String[]{word});
                String data = null;
                while (cursor.moveToNext()) {
                    data = cursor.getString(0);
                }
                cursor.close();
                mPersianDatabaseAccess.closeDatabase();
                return data;
            }
        }).subscribeOn(Schedulers.io());
    }

    public Flowable<List<Word>> getAllWords() {
        return Flowable.fromCallable(() -> {
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
        }).subscribeOn(Schedulers.io());
    }


}
