package com.example.dileit.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dileit.constant.KeysValue;

public class SharedPreferenceUtil {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public SharedPreferenceUtil(Context context) {
        mSharedPreferences = context.getSharedPreferences(KeysValue.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public int getHour() {
        return mSharedPreferences.getInt(KeysValue.SP_HOUR, -1);
    }

    public int getMin() {
        return mSharedPreferences.getInt(KeysValue.SP_MIN, -1);
    }

    public void setTime(int h, int m) {
        mEditor = mSharedPreferences.edit();
        mEditor.putInt(KeysValue.SP_HOUR, h);
        mEditor.putInt(KeysValue.SP_MIN, m);
        mEditor.apply();
    }
}
