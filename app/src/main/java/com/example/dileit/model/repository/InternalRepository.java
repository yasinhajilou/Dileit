package com.example.dileit.model.repository;

import android.content.Context;

import com.example.dileit.model.dao.LeitnerDao;
import com.example.dileit.model.dao.WordHistoryDao;
import com.example.dileit.model.database.InternalRoomDatabase;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.model.entity.WordHistory;
import com.example.dileit.utils.LeitnerUtils;
import com.example.dileit.viewmodel.InternalViewModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class InternalRepository {
    private WordHistoryDao mWordHistoryDao;
    private LeitnerDao mLeitnerDao;
    private String TAG = InternalViewModel.class.getSimpleName();

    public InternalRepository(Context context) {
        InternalRoomDatabase database = InternalRoomDatabase.getInstance(context);
        mWordHistoryDao = database.mWordHistoryDao();
        mLeitnerDao = database.mLeitnerDao();
    }


    //get data(Queries)
    public Flowable<List<WordHistory>> getAllWordHistory() {
        return mWordHistoryDao.getAllWordHistory()
                .subscribeOn(Schedulers.io());
    }

    public Flowable<WordHistory> getWordHistoryInfo(String word) {
        return mWordHistoryDao.getWordInformation(word)
                .subscribeOn(Schedulers.io());
    }

    public Flowable<Leitner> getLeitnerInfoByWord(String word) {
        return mLeitnerDao.getLeitnerInfoByWord(word)
                .subscribeOn(Schedulers.io());
    }

    public Maybe<Leitner> getExistingLeitner(String word){
        return mLeitnerDao.getExistingLeitner(word)
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


    public Flowable<List<Leitner>> getTodayList() {
        return getAllLeitnerItems().concatMap(leitners -> Flowable.fromCallable(() -> LeitnerUtils.getPreparedLeitnerItems(leitners)));
    }

    public Flowable<Integer> getTodayListSize() {
        return getTodayList().map(leitners -> {
            if (leitners != null)
                return leitners.size();
            else
                return 0;
        });
    }

    //insert data
    public Completable insertWordHistory(WordHistory wordHistory) {
        return mWordHistoryDao.Insert(wordHistory)
                .subscribeOn(Schedulers.io());
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

    public Completable updateWordHistory(WordHistory wordHistory) {
        return mWordHistoryDao.update(wordHistory);
    }


    //Delete Item
    public Single<Integer> deleteLeitnerItem(Leitner leitner) {
        return mLeitnerDao.delete(leitner)
                .subscribeOn(Schedulers.io());
    }


}
