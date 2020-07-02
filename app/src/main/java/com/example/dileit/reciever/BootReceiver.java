package com.example.dileit.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.dileit.utils.AlarmManagerUtils;
import com.example.dileit.utils.SharedPreferenceUtil;
import com.google.android.gms.common.util.SharedPreferencesUtils;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            SharedPreferenceUtil sharedPreferencesUtils = new SharedPreferenceUtil(context);
            int h = sharedPreferencesUtils.getHour();
            int m = sharedPreferencesUtils.getMin();
            if (h != -1 && m != -1) {
                AlarmManagerUtils alarmManagerUtils = new AlarmManagerUtils(context);
                alarmManagerUtils.setAlarm(h, m);
            }
        }
    }
}
