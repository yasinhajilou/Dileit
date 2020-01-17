package com.example.dileit.model.entity;

import androidx.room.PrimaryKey;

public class Leitner {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int state;

    private int repeatCounter;

    private int lastCheck;

    public Leitner(int id, int state, int repeatCounter, int lastCheck) {
        this.id = id;
        this.state = state;
        this.repeatCounter = repeatCounter;
        this.lastCheck = lastCheck;
    }

    public int getId() {
        return id;
    }

    public int getState() {
        return state;
    }

    public int getRepeatCounter() {
        return repeatCounter;
    }

    public int getLastCheck() {
        return lastCheck;
    }
}
