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
    LiveData<List<WordHistory>> getAllData();

    @Transaction
    @Query("SELECT * FROM WordHistory where leitnerId LIKE :leitnerId LIMIT 1")
    WordHistory getWordByLeitnerId(int leitnerId);
}
