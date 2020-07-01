package com.example.dileit.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;

import com.example.dileit.R;
import com.example.dileit.constant.KeysValue;
import com.example.dileit.view.fragment.TimePickerDialogFragment;
import com.example.dileit.databinding.ActivitySettingBinding;
import com.example.dileit.reciever.AlarmReceiver;
import com.example.dileit.viewmodel.InternalViewModel;
import com.example.dileit.viewmodel.TimeSharedViewModel;

import java.util.Calendar;

public class SettingActivity extends AppCompatActivity {

    private InternalViewModel mInternalViewModel;
    private TimeSharedViewModel mTimeSharedViewModel;
    private ActivitySettingBinding mBinding;
    private AlarmManager mAlarmManager;
    private final int REQ_CODE_ALARM_REC = 110;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mSharedPreferences = getSharedPreferences(getString(R.string.shared_preference), MODE_PRIVATE);

        mInternalViewModel = ViewModelProviders.of(this).get(InternalViewModel.class);
        mTimeSharedViewModel = ViewModelProviders.of(this).get(TimeSharedViewModel.class);

        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        int lastHour = mSharedPreferences.getInt(KeysValue.SP_HOUR, -1);
        int lastMin = mSharedPreferences.getInt(KeysValue.SP_MIN, -1);
        if (lastHour != -1 && lastMin != -1) {
            initTextViews(lastHour, lastMin);
        }

        if (checkForExistingAlarm() != null) {
            mBinding.switchReminder.setChecked(true);
            handleTimePickerEnabling(true);
        } else {
            mBinding.switchReminder.setChecked(false);
            handleTimePickerEnabling(false);
        }

        mBinding.switchReminder.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                handleTimePickerEnabling(true);
                //it's fist time for setting alarm
                if (lastHour == -1 && lastMin == -1) {
                    showTimePicker();
                }
            } else {
                handleTimePickerEnabling(false);
                if (checkForExistingAlarm() != null) {
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, REQ_CODE_ALARM_REC, new Intent(this, AlarmReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
                    mAlarmManager.cancel(pendingIntent);
                    pendingIntent.cancel();
                }
            }
        });
        mBinding.linearLayoutTimeEditor.setOnClickListener(view -> {
            showTimePicker();
        });

        mTimeSharedViewModel.getTime().observe(this, ints -> {

            int hour = ints[0];
            int min = ints[1];

            initTextViews(hour, min);

            mEditor = mSharedPreferences.edit();
            mEditor.putInt(KeysValue.SP_HOUR, hour);
            mEditor.putInt(KeysValue.SP_MIN, min);
            mEditor.apply();

            setAlarm(hour, min);
        });
    }

    private void handleTimePickerEnabling(boolean b) {
        mBinding.tvReminderH.setEnabled(b);
        mBinding.tvReminderM.setEnabled(b);
        mBinding.linearLayoutTimeEditor.setEnabled(b);
    }

    private PendingIntent checkForExistingAlarm() {
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        return PendingIntent.getBroadcast(this,
                REQ_CODE_ALARM_REC, alarmIntent, PendingIntent.FLAG_NO_CREATE);
    }

    private void setAlarm(int h, int m) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, m);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, REQ_CODE_ALARM_REC,
                new Intent(this, AlarmReceiver.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void initTextViews(int h, int m) {
        mBinding.tvReminderH.setText(String.valueOf(h));
        mBinding.tvReminderM.setText(String.valueOf(m));
    }

    private void showTimePicker() {
        TimePickerDialogFragment timePickerDialogFragment = new TimePickerDialogFragment();
        timePickerDialogFragment.show(getSupportFragmentManager(), "DIALOG_TIME_PICKER");
    }
}