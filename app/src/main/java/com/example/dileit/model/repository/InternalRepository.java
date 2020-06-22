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

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class InternalRepository {
    private WordHistoryDao mWordHistoryDao;
    private LiveData<List<WordHistory>> mAllWordHistory;
    private LeitnerDao mLeitnerDao;
    private String TAG = InternalViewModel.class.getSimpleName();

    public InternalRepository(Context context) {
        InternalRoomDatabase database = InternalRoomDatabase.getInstance(context);
        mWordHistoryDao = database.mWordHistoryDao();
        mLeitnerDao = database.mLeitnerDao();
        mAllWordHistory = mWordHistoryDao.getAllWordHistory();
    }


    //get data(Queries)
    public LiveData<List<WordHistory>> getAllWordHistory() {
        return mAllWordHistory;
    }

    public LiveData<WordHistory> getWordHistoryInfo(String word) {
        return mWordHistoryDao.getWordInformation(word);
    }

    public Flowable<Leitner> getLeitnerInfoByWord(String word) {
        return mLeitnerDao.leitnerInfoByWord(word)
                .subscribeOn(Schedulers.io());
    }

    public Flowable<List<Leitner>> getAllLeitnerItems() {
        return mLeitnerDao.LEITNER_LIST()
                .subscribeOn(Schedulers.io());
    }

    public Flowable<List<Leitner>> getCardByState(int state) {
        return mLeitnerDao.getCardByState(state)
                .subscribeOn(Schedulers.io());
    }

    public Flowable<List<Leitner>> getGetCardsByRangeState(int start, int end) {
        return mLeitnerDao.getCardsByRangeState(start, end)
                .subscribeOn(Schedulers.io());
    }

    public Flowable<Leitner> getLeitnerCardById(int id) {
        return mLeitnerDao.getLeitnerCardById(id)
                .subscribeOn(Schedulers.io());
    }


    //insert data
    public void insertWordHistory(int leitnerId, Long time, String word, int engId) {
        new InsertWordHistory(leitnerId, time, word, engId).execute();
    }

    public Single<Long> insertLeitnerItem(Leitner leitner) {
        return mLeitnerDao.insert(leitner)
                .subscribeOn(Schedulers.io());
    }


    //update data
    public Completable updateLeitnerItem(Leitner leitner) {
        return mLeitnerDao.update(leitner)
                .subscribeOn(Schedulers.io());
    }

    public void updateWordHistory(WordHistory wordHistory) {
        new UpdateWordHistory().execute(wordHistory);
    }


    //Delete Item
    public Single<Integer> deleteLeitnerItem(Leitner leitner) {
        return mLeitnerDao.delete(leitner)
                .subscribeOn(Schedulers.io());
    }


    //Async classes

    private class InsertWordHistory extends AsyncTask<Void, Void, Void> {
        private int leitnerId;
        private Long time;
        private String word;
        private int engId;

        public InsertWordHistory(int leitnerId, Long time, String word, int engId) {
            this.leitnerId = leitnerId;
            this.time = time;
            this.word = word;
            this.engId = engId;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            WordHistory wordHistory = new WordHistory(word, engId, leitnerId, time);
            mWordHistoryDao.Insert(wordHistory);
            Log.d(TAG, "doInBackground: added");
            return null;
        }
    }

    private class UpdateWordHistory extends AsyncTask<WordHistory, Void, Void> {

        @Override
        protected Void doInBackground(WordHistory... wordHistories) {
            mWordHistoryDao.update(wordHistories[0]);
            return null;
        }
    }

}
