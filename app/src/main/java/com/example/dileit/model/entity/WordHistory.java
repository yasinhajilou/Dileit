package com.example.dileit.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WordHistory {

    @PrimaryKey
    private String word;

    private String wordDef;

    private int leitnerId;

    private long addedTime;

    public WordHistory(String word, String wordDef, int leitnerId, long addedTime) {
        this.word = word;
        this.wordDef = wordDef;
        this.leitnerId = leitnerId;
        this.addedTime = addedTime;
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
