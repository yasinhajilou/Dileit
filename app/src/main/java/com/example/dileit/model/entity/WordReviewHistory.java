package com.example.dileit.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WordReviewHistory {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String cardTitle;

    private long reviewedTime;

    public WordReviewHistory(int id, String cardTitle, long reviewedTime) {
        this.id = id;
        this.cardTitle = cardTitle;
        this.reviewedTime = reviewedTime;
    }

    public int getId() {
        return id;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public long getReviewedTime() {
        return reviewedTime;
    }
}
