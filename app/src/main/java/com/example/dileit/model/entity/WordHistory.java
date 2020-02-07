package com.example.dileit.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WordHistory {

    @PrimaryKey
    @NonNull
    private String word;

    private String wordDef;

    private long leitnerId;

    private long addedTime;

    public WordHistory(@NonNull String word, String wordDef, long leitnerId, long addedTime) {
        this.word = word;
        this.wordDef = wordDef;
        this.leitnerId = leitnerId;
        this.addedTime = addedTime;
    }


    public void setLeitnerId(long leitnerId) {
        if (leitnerId >= 0)
            this.leitnerId = leitnerId;
    }

    public String getWord() {
        return word;
    }

    public String getWordDef() {
        return wordDef;
    }

    public long getLeitnerId() {
        return leitnerId;
    }

    public long getAddedTime() {
        return addedTime;
    }
}
