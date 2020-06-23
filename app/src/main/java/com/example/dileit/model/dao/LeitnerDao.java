package com.example.dileit.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dileit.model.entity.Leitner;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
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

    @Query("SELECT * FROM Leitner WHERE word LIKE :word")
    Flowable<Leitner> leitnerInfoByWord(String word);

    @Query("SELECT * FROM Leitner WHERE state LIKE :cardState")
    Flowable<List<Leitner>> getCardByState(int cardState);

    @Query("SELECT * FROM Leitner WHERE state BETWEEN :start AND :end")
    Flowable<List<Leitner>> getCardsByRangeState(int start, int end);

    @Query("SELECT * FROM Leitner WHERE id LIKE :cardId")
    Flowable<Leitner> getLeitnerCardById(int cardId);
}
