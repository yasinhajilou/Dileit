package com.example.dileit.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.dileit.model.entity.WordHistory;

import java.util.List;

public interface WordHistoryDao {

    @Insert
    void Insert(WordHistory wordHistory);

    @Update
    void update(WordHistory wordHistory);

    @Delete
    void delete(WordHistory wordHistory);

    @Transaction
    @Query("SELECT * FROM WordHistory")
    LiveData<List<WordHistory>> getAllData();

}
