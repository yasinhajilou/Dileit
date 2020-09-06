package com.example.dileit.constant;

import android.content.Context;

import com.example.dileit.R;

public class TimeReporterFilter {
    public static final int DAY = 0;
    public static final int WEEK = 1;
    public static final int MONTH = 2;
    public static final int YEAR = 3;

    public static  String DAY_SPECIFIER(Context context) {
        return context.getString(R.string.last_24_h);
    }
    public static  String WEEK_SPECIFIER(Context context) {
        return context.getString(R.string.last_seven_day);
    }
    public static  String MONTH_SPECIFIER(Context context) {
        return context.getString(R.string.last_month);
    }
    public static  String YEAR_SPECIFIER(Context context) {
        return context.getString(R.string.last_year);
    }

}
