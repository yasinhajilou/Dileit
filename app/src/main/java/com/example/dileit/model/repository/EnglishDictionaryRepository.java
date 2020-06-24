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
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class EnglishDictionaryRepository {
    private EnglishDatabaseAccess mDatabaseAccess;

    public EnglishDictionaryRepository(Application application) {
        mDatabaseAccess = EnglishDatabaseAccess.getINSTANCE(application);
    }


    //search in keys table for finding typed word
    public Flowable<List<SearchDictionary>> doSearch(String word) {
        return Flowable.fromCallable(() -> {
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

            mDatabaseAccess.closeDatabase();

            if (cursor != null) {
                cursor.close();
            }

            return wordEnglishDics;
        }).subscribeOn(Schedulers.io());
    }

    public Flowable<List<EnglishDef>> getRefById(int engId) {
        return Flowable.fromCallable(() -> {
            mDatabaseAccess.openDatabase();
            List<EnglishDef> englishDefs = new ArrayList<>();

            Cursor cursor = mDatabaseAccess.getDatabase().rawQuery("SELECT definition,category,synonyms,examples FROM description WHERE _id like ?", new String[]{String.valueOf(engId)});
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String def = cursor.getString(0);
                    String cat = cursor.getString(1);
                    String syn = cursor.getString(2);
                    String exam = cursor.getString(3);
                    EnglishDef englishDic = new EnglishDef(cat, def, syn, exam);
                    englishDefs.add(englishDic);
                }
            }

            mDatabaseAccess.closeDatabase();
            if (cursor != null) {
                cursor.close();
            }
            return englishDefs;
        }).subscribeOn(Schedulers.io());
    }
}
