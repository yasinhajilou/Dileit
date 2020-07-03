package com.example.dileit.model;

public class LeitnerReport {

    private long lastReviewTime;

    private long timeAdded;

    public LeitnerReport(long lastReviewTime, long timeAdded) {
        this.lastReviewTime = lastReviewTime;
        this.timeAdded = timeAdded;
    }

    public long getLastReviewTime() {
        return lastReviewTime;
    }

    public long getTimeAdded() {
        return timeAdded;
    }
}
