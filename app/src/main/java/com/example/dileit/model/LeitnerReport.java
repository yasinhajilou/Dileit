package com.example.dileit.model;

public class LeitnerReport implements Comparable<LeitnerReport> {

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

    @Override
    public int compareTo(LeitnerReport leitnerReport) {
        return Long.compare(leitnerReport.lastReviewTime, this.lastReviewTime);
    }
}
