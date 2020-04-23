package com.example.dileit.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Comparator;

@Entity
public class Leitner implements Comparable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String word;

    private String def;

    private String secondDef;

    private String wordAct;

    private String guide;

    private int state;

    private int repeatCounter;

    private long lastReviewTime;

    private long timeAdded;

    public Leitner(int id, String word, String def, String secondDef, String wordAct, String guide, int state, int repeatCounter, long lastReviewTime, long timeAdded) {
        this.id = id;
        this.word = word;
        this.def = def;
        this.state = state;
        this.repeatCounter = repeatCounter;
        this.lastReviewTime = lastReviewTime;
        this.timeAdded = timeAdded;
        this.wordAct = wordAct;
        this.secondDef = secondDef;
        this.guide = guide;
    }


    public void setState(int state) {
        this.state = state;
    }

    public void setRepeatCounter(int repeatCounter) {
        this.repeatCounter = repeatCounter;
    }

    public void setLastReviewTime(long lastReviewTime) {
        this.lastReviewTime = lastReviewTime;
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

    public String getSecondDef() {
        return secondDef;
    }

    public String getGuide() {
        return guide;
    }

    @Override
    public int compareTo(Object o) {
        Leitner leitner = (Leitner) o;
        //we want to show smaller number(data) at first
        return Integer.compare(leitner.getState(), this.state) * -1;
    }
}
