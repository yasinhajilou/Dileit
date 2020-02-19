package com.example.dileit.model.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.dileit.model.dao.LeitnerDao;
import com.example.dileit.model.dao.WordHistoryDao;
import com.example.dileit.model.database.InternalRoomDatabase;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.model.entity.WordHistory;
import com.example.dileit.viewmodel.InternalViewModel;
import com.example.dileit.viewmodel.vminterface.InternalInterface;

import java.util.List;

public class InternalRepository {
    private WordHistoryDao mDao;
    private LiveData<List<WordHistory>> mAllWordHistory;
    private LeitnerDao mLeitnerDao;
    private String TAG = InternalViewModel.class.getSimpleName();

    public InternalRepository(Context context) {
        InternalRoomDatabase database = InternalRoomDatabase.getInstance(context);
        mDao = database.mWordHistoryDao();
        mLeitnerDao = database.mLeitnerDao();
        mAllWordHistory = mDao.getAllWordHistory();
    }


    //get data(Queries)
    public LiveData<List<WordHistory>> getAllWordHistory() {
        return mAllWordHistory;
    }

    public LiveData<WordHistory> getWordHistoryInfo(String word) {
        return mDao.getWordInformation(word);
    }

    public LiveData<Leitner> getLeitnerInfoByWord(String word){
        return mLeitnerDao.leitnerInfoByWord(word);
    }


    //insert data
    public void insertWordHistory(int leitnerId, Long time, String word, String wordDef) {
        new InsertWordHistory(leitnerId, time, word, wordDef).execute();
    }

    public void insertLeitnerItem(Leitner leitner, InternalInterface anInterface) {
        new InsertLeitnerItem(anInterface).execute(leitner);
    }


    //update data
    public void updateLeitnerItem(Leitner leitner) {
        new UpdateLeitnerItem().execute(leitner);
    }

    public void updateWordHistory(WordHistory wordHistory) {
        new UpdateWordHistory().execute(wordHistory);
    }


    private class InsertLeitnerItem extends AsyncTask<Leitner, Void, Long> {
        InternalInterface mInterface;

         InsertLeitnerItem(InternalInterface anInterface) {
            mInterface = anInterface;
        }

        @Override
        protected Long doInBackground(Leitner... leitners) {
            return mLeitnerDao.insert(leitners[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            mInterface.onLeitnerAdded(aLong);
        }
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

    private class UpdateLeitnerItem extends AsyncTask<Leitner, Void, Void> {

        @Override
        protected Void doInBackground(Leitner... leitners) {
            mLeitnerDao.update(leitners[0]);
            return null;
        }
    }

    private class UpdateWordHistory extends AsyncTask<WordHistory, Void, Void> {

        @Override
        protected Void doInBackground(WordHistory... wordHistories) {
            mDao.update(wordHistories[0]);
            return null;
        }
    }
}
