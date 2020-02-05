package com.example.dileit.model.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.dileit.model.dao.WordHistoryDao;
import com.example.dileit.model.database.InternalRoomDatabase;
import com.example.dileit.model.entity.WordHistory;
import com.example.dileit.viewmodel.InternalInterface;
import com.example.dileit.viewmodel.InternalViewModel;

import java.util.List;

public class InternalRepository {
    private InternalRoomDatabase mDatabase;
    private WordHistoryDao mDao;
    private LiveData<List<WordHistory>> mLiveData;
    private String TAG = InternalViewModel.class.getSimpleName();

    public InternalRepository(Context context) {
        mDatabase = InternalRoomDatabase.getInstance(context);
        mDao = mDatabase.mWordHistoryDao();
        mLiveData = mDao.getAllData();
    }

    public LiveData<List<WordHistory>> getLiveData() {
        return mLiveData;
    }

    public void insertWordHistory(int leitnerId, Long time, String word, String wordDef) {
        new InsertWordHistory(leitnerId, time, word, wordDef).execute();
    }


    private class InsertWordHistory extends AsyncTask<Void, Void, Void> {
        private int leitnerId;
        private Long time;
        private String word, wordDef;

        InsertWordHistory(int leitnerId, Long time, String word, String wordDef) {
            this.leitnerId = leitnerId;
            this.time = time;
            this.word = word;
            this.wordDef = wordDef;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            WordHistory wordHistory = new WordHistory(word, wordDef, leitnerId, time);
            mDao.Insert(wordHistory);
            Log.d(TAG, "doInBackground: added");
            return null;
        }
    }
}
