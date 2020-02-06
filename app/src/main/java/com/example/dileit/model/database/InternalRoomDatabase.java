package com.example.dileit.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dileit.model.dao.WordHistoryDao;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.model.entity.WordHistory;

@Database(entities = {Leitner.class, WordHistory.class}, version = 1, exportSchema = false)
public abstract class InternalRoomDatabase extends RoomDatabase {
    public abstract WordHistoryDao mWordHistoryDao();

    private static InternalRoomDatabase INSTANCE;

    public static InternalRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (InternalRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, InternalRoomDatabase.class, "Dileit_DB")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}
