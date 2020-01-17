package com.example.dileit.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WordHistory {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String word;

    private String wordDef;

    private int leitnerId;

    private long addedTime;

    public WordHistory(int id, String word, String wordDef, int leitnerId, long addedTime) {
        this.id = id;
        this.word = word;
        this.wordDef = wordDef;
        this.leitnerId = leitnerId;
        this.addedTime = addedTime;
    }


    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getWordDef() {
        return wordDef;
    }

    public int getLeitnerId() {
        return leitnerId;
    }

    public long getAddedTime() {
        return addedTime;
    }
}
