package com.example.dileit.model.repository;

import android.content.Context;

import com.example.dileit.model.LeitnerReport;
import com.example.dileit.model.dao.LeitnerDao;
import com.example.dileit.model.dao.WordHistoryDao;
import com.example.dileit.model.dao.WordReviewHistoryDao;
import com.example.dileit.model.database.InternalRoomDatabase;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.model.entity.WordHistory;
import com.example.dileit.model.entity.WordReviewHistory;
import com.example.dileit.utils.LeitnerUtils;
import com.example.dileit.viewmodel.InternalViewModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class InternalRepository {
    private WordHistoryDao mWordHistoryDao;
    private LeitnerDao mLeitnerDao;
    private WordReviewHistoryDao mWordReviewHistoryDao;
    private String TAG = InternalViewModel.class.getSimpleName();

    public InternalRepository(Context context) {
        InternalRoomDatabase database = InternalRoomDatabase.getInstance(context);
        mWordHistoryDao = database.mWordHistoryDao();
        mLeitnerDao = database.mLeitnerDao();
        mWordReviewHistoryDao = database.mWordReviewHistoryDao();
    }


    //get data(Queries)
    public Flowable<List<WordReviewHistory>> getWordReviewedHistoryByRange(long s, long e) {
        return mWordReviewHistoryDao.LIST_FLOWABLE(s, e)
                .subscribeOn(Schedulers.io());
    }

    public Flowable<Integer> getWordReviewedHistoryCount() {
        return mWordReviewHistoryDao.LIST()
                .subscribeOn(Schedulers.io());
    }
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

    public Maybe<Leitner> getExistingLeitner(String word) {
        return mLeitnerDao.getExistingLeitner(word)
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

    public Flowable<List<Leitner>> getAllLeitnerItems() {
        return mLeitnerDao.LEITNER_LIST()
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


    public Single<Integer> getTodayListSizeSingle() {
        return mLeitnerDao.getAllCards()
                .map(leitners -> {
                    int todaySize = LeitnerUtils.getPreparedLeitnerItems(leitners).size();
                    return Math.max(todaySize, 0);
                })
                .subscribeOn(Schedulers.io());
    }


    public Flowable<Integer> getNewCardsCount() {
        return mLeitnerDao.getNewCardsCount();
    }

    public Flowable<Integer> getLearnedCardsCount() {
        return mLeitnerDao.getLearnedCardsCount();
    }

    public Flowable<Integer> getReviewingCardCount() {
        return mLeitnerDao.getReviewingCardCount();
    }


    public Flowable<Integer> getAllCardCount() {
        return mLeitnerDao.getAllLeitnerCount()
                .subscribeOn(Schedulers.io());
    }

    public Flowable<List<LeitnerReport>> getReviewedCardByRange(long start, long end) {
        return mLeitnerDao.getReviewedCardForReportByRange(start, end)
                .subscribeOn(Schedulers.io());
    }

    public Flowable<List<LeitnerReport>> getAddedCardByRange(long stat, long end) {
        return mLeitnerDao.getAddCardForReportByRange(stat, end)
                .subscribeOn(Schedulers.io());
    }

    //insert data
    public Completable insetWordReviewedHistory(WordReviewHistory wordReviewHistory){
        return mWordReviewHistoryDao.insert(wordReviewHistory)
                .subscribeOn(Schedulers.io());
    }

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

    public Completable deleteAllHistory() {
        return mWordHistoryDao.deleteAllWordHistory()
                .subscribeOn(Schedulers.io());
    }


}
