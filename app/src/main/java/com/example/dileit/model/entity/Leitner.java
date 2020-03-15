package com.example.dileit.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Leitner {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String word;

    private String def;

    private String secondDef;

    private String wordAct;

    private int state;

    private int repeatCounter;

    private long lastReviewTime;

    private long timeAdded;

    public Leitner(int id, String word,String secondDef,String wordAct, String def, int state, int repeatCounter, long lastReviewTime, long timeAdded) {
        this.id = id;
        this.word = word;
        this.def = def;
        this.state = state;
        this.repeatCounter = repeatCounter;
        this.lastReviewTime = lastReviewTime;
        this.timeAdded = timeAdded;
        this.wordAct = wordAct;
        this.secondDef = secondDef;
    }


    public String getWordAct() {
        return wordAct;
    }

    public long getTimeAdded() {
        return timeAdded;
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

    public long getLastReviewTime() {
        return lastReviewTime;
    }

    public String getWord() {
        return word;
    }

    public String getDef() {
        return def;
    }
}
