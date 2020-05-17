package com.example.dileit.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dileit.constant.LeitnerStateConstant;
import com.example.dileit.model.entity.Leitner;

import java.util.LinkedList;
import java.util.List;

@Dao
public interface LeitnerDao {
    @Insert
    long insert(Leitner leitner);

    @Update
    void update(Leitner leitner);

    @Delete
    int delete(Leitner leitner);

    @Query("SELECT * FROM  Leitner")
    LiveData<List<Leitner>> LEITNER_LIST();

    @Query("SELECT * FROM Leitner WHERE word LIKE :word")
    LiveData<Leitner> leitnerInfoByWord(String word);

    @Query("SELECT * FROM Leitner WHERE state LIKE :cardState")
    LiveData<List<Leitner>> getCardByState(int cardState);

    @Query("SELECT * FROM Leitner WHERE state BETWEEN :start AND :end")
    LiveData<List<Leitner>> getCardsByRangeState(int start, int end);

    @Query("SELECT * FROM Leitner WHERE id LIKE :cardId")
    LiveData<Leitner> getLeitnerCardById(int cardId);
}
