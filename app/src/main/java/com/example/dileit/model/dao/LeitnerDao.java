package com.example.dileit.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dileit.model.LeitnerReport;
import com.example.dileit.model.entity.Leitner;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface LeitnerDao {
    @Insert
    Single<Long> insert(Leitner leitner);

    @Update
    Completable update(Leitner leitner);

    @Delete
    Single<Integer> delete(Leitner leitner);

    @Query("SELECT * FROM  Leitner")
    Flowable<List<Leitner>> LEITNER_LIST();

    @Query("SELECT * FROM  Leitner")
    Single<List<Leitner>> getAllCards();

    @Query("SELECT * FROM Leitner WHERE word LIKE :word")
    Flowable<Leitner> getLeitnerInfoByWord(String word);

    @Query("SELECT * FROM Leitner WHERE state LIKE :cardState")
    Flowable<List<Leitner>> getCardByState(int cardState);

    @Query("SELECT * FROM Leitner WHERE state BETWEEN :start AND :end")
    Flowable<List<Leitner>> getCardsByRangeState(int start, int end);

    @Query("SELECT * FROM Leitner WHERE id LIKE :cardId")
    Flowable<Leitner> getLeitnerCardById(int cardId);

    @Query("SELECT * FROM Leitner WHERE word LIKE :word")
    Maybe<Leitner> getExistingLeitner(String word);

    @Query("SELECT COUNT(id) FROM Leitner WHERE state LIKE 0")
    Flowable<Integer> getNewCardsCount();

    @Query("SELECT COUNT(id) FROM Leitner WHERE state LIKE 6")
    Flowable<Integer> getLearnedCardsCount();

    @Query("SELECT COUNT(id) FROM Leitner WHERE state BETWEEN 1 AND 5")
    Flowable<Integer> getReviewingCardCount();

    @Query("SELECT COUNT(id) FROM Leitner")
    Flowable<Integer> getAllLeitnerCount();

    @Query("select timeAdded,lastReviewTime FROM Leitner WHERE lastReviewTime BETWEEN :start AND :end")
    Flowable<List<LeitnerReport>> getReviewedCardForReportByRange(long start, long end);

    @Query("select timeAdded,lastReviewTime FROM Leitner WHERE timeAdded BETWEEN :start AND :end")
    Flowable<List<LeitnerReport>> getAddCardForReportByRange(long start, long end);

    @Query("SELECT COUNT(id) FROM Leitner WHERE timeAdded BETWEEN :start AND :end")
    Maybe<Integer> getAddedCardsCountByTimeRange(long start , long end);
}
