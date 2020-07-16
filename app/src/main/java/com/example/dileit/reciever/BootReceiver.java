package com.example.dileit.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.dileit.utils.AlarmManagerUtils;
import com.example.dileit.utils.SharedPreferenceUtil;
import com.google.android.gms.common.util.SharedPreferencesUtils;

public class BootReceiver extends BroadcastReceiver {

    private String TAG = BootReceiver.class.getSimpleName()+"Tag";

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferenceUtil sharedPreferencesUtils = new SharedPreferenceUtil(context);
        boolean alarmStatus = sharedPreferencesUtils.getAlarmStatus();
        Log.d(TAG, "onReceive: ");
        if (alarmStatus) {
            Log.d(TAG, "onReceive: " + alarmStatus);
            int h = sharedPreferencesUtils.getHour();
            int m = sharedPreferencesUtils.getMin();
            AlarmManagerUtils alarmManagerUtils = new AlarmManagerUtils(context);
            alarmManagerUtils.setAlarm(h, m);

        }
    }
}
