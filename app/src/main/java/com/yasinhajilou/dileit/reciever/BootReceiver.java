package com.yasinhajilou.dileit.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yasinhajilou.dileit.utils.AlarmManagerUtils;
import com.yasinhajilou.dileit.utils.SharedPreferenceUtil;

public class BootReceiver extends BroadcastReceiver {

    private String TAG = BootReceiver.class.getSimpleName()+"Tag";

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferenceUtil sharedPreferencesUtils = new SharedPreferenceUtil(context);
        boolean alarmStatus = sharedPreferencesUtils.getAlarmStatus();
        if (alarmStatus) {
            int h = sharedPreferencesUtils.getHour();
            int m = sharedPreferencesUtils.getMin();
            AlarmManagerUtils alarmManagerUtils = new AlarmManagerUtils(context);
            alarmManagerUtils.setAlarm(h, m);

        }
    }
}
