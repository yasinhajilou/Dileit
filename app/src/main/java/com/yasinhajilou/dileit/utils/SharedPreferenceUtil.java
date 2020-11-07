package com.yasinhajilou.dileit.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.config.proto.Config;
import com.yasinhajilou.dileit.constant.KeysValue;

public class SharedPreferenceUtil {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public SharedPreferenceUtil(Context context) {
        mSharedPreferences = context.getSharedPreferences(KeysValue.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public void setAlarmManagerStatus(boolean b) {
        mEditor.putBoolean(KeysValue.SP_ALARM_STATUS, b);
        mEditor.apply();
    }

    public boolean getAlarmStatus() {
        return mSharedPreferences.getBoolean(KeysValue.SP_ALARM_STATUS, false);
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

    public void setUserFirstTime(boolean state){
        mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(KeysValue.SP_USER_STATE , state);
        mEditor.apply();
    }

    public boolean isUserFirstTime(){
        return mSharedPreferences.getBoolean(KeysValue.SP_USER_STATE , true);
    }
}
