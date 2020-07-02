package com.example.dileit.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.example.dileit.reciever.AlarmReceiver;
import com.example.dileit.reciever.BootReceiver;

import java.util.Calendar;

public class AlarmManagerUtils {
    private AlarmManager mAlarmManager;
    private final int REQ_CODE_ALARM_REC = 110;
    private Context mContext;
    private PackageManager mPackageManager;

    public AlarmManagerUtils(Context context) {
        mContext = context;
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public PendingIntent checkForExistingAlarm() {
        Intent alarmIntent = new Intent(mContext, AlarmReceiver.class);
        return PendingIntent.getBroadcast(mContext,
                REQ_CODE_ALARM_REC, alarmIntent, PendingIntent.FLAG_NO_CREATE);
    }

    public void setAlarm(int h, int m) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, m);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, REQ_CODE_ALARM_REC,
                new Intent(mContext, AlarmReceiver.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        enableBootReceiver();

    }

    public void cancelAlarm() {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, REQ_CODE_ALARM_REC, new Intent(mContext, AlarmReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
        disableBootReceiver();
    }


    private void enableBootReceiver() {
        ComponentName componentName = new ComponentName(mContext, BootReceiver.class);
        mPackageManager = mContext.getPackageManager();
        mPackageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    private void disableBootReceiver() {
        ComponentName componentName = new ComponentName(mContext, BootReceiver.class);
        mPackageManager = mContext.getPackageManager();

        mPackageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}
