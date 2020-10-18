package com.yasinhajilou.dileit.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.yasinhajilou.dileit.model.entity.WordHistory;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface WordHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable Insert(WordHistory wordHistory);

    @Update
    Completable update(WordHistory wordHistory);

    @Delete
    Completable delete(WordHistory wordHistory);

    @Query("SELECT * FROM WordHistory")
    Flowable<List<WordHistory>> getAllWordHistory();

    @Query("SELECT * FROM WordHistory where leitnerId LIKE :leitnerId LIMIT 1")
    Flowable<WordHistory> getWordByLeitnerId(int leitnerId);


    @Query("SELECT * FROM  WordHistory where word LIKE :exactWord")
    Flowable<WordHistory> getWordInformation(String exactWord);

    @Query("DELETE FROM WordHistory")
    Completable deleteAllWordHistory();

}
