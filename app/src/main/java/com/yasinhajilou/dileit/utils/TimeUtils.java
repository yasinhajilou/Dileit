package com.yasinhajilou.dileit.utils;

public class TimeUtils {

    public static long getDaysBetweenTimestamps(long start , long end){
        long diff = end - start;
        return diff / (24 * 60 * 60 * 1000);
    }

    public static long getHoursBetweenTimeStamps(long start , long end){
        long diff = end - start;
        long remain = diff % (24 * 60 * 60 * 1000);
        return remain / (60 * 60 * 1000);
    }

}
