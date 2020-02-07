package com.example.dileit.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.dileit.model.entity.WordHistory;

import java.util.List;

@Dao
public interface WordHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(WordHistory wordHistory);

    @Update
    void update(WordHistory wordHistory);

    @Delete
    void delete(WordHistory wordHistory);

    @Transaction
    @Query("SELECT * FROM WordHistory")
    LiveData<List<WordHistory>> getAllWordHistory();

    @Transaction
    @Query("SELECT * FROM WordHistory where leitnerId LIKE :leitnerId LIMIT 1")
    LiveData<WordHistory> getWordByLeitnerId(int leitnerId);

    @Transaction
    @Query("SELECT * FROM  WordHistory where word LIKE :exactWord")
    LiveData<WordHistory> getWordInformation(String exactWord);

}
