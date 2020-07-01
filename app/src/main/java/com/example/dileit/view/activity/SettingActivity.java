package com.example.dileit.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mInternalViewModel = ViewModelProviders.of(this).get(InternalViewModel.class);
        mTimeSharedViewModel = ViewModelProviders.of(this).get(TimeSharedViewModel.class);

        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent alarmIntent = new Intent(this, AlarmReceiver.class);

        if (PendingIntent.getBroadcast(this,
                REQ_CODE_ALARM_REC, alarmIntent, PendingIntent.FLAG_NO_CREATE) != null) {
            mBinding.switchReminder.setChecked(true);
        } else {
            mBinding.switchReminder.setChecked(false);
        }

        mBinding.switchReminder.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                mBinding.tvReminderH.setEnabled(true);
                mBinding.tvReminderM.setEnabled(true);
                mBinding.linearLayoutTimeEditor.setEnabled(true);
            } else {
                mBinding.tvReminderH.setEnabled(false);
                mBinding.tvReminderM.setEnabled(false);
                mBinding.linearLayoutTimeEditor.setEnabled(false);
            }
        });
        mBinding.linearLayoutTimeEditor.setOnClickListener(view -> {
            TimePickerDialogFragment timePickerDialogFragment = new TimePickerDialogFragment();
            timePickerDialogFragment.show(getSupportFragmentManager(), "DIALOG_TIME_PICKER");
        });

        mTimeSharedViewModel.getTime().observe(this, ints -> {
            int hour = ints[0];
            int min = ints[1];
            mBinding.tvReminderH.setText(String.valueOf(hour));
            mBinding.tvReminderM.setText(String.valueOf(min));
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, min);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, REQ_CODE_ALARM_REC,
                    new Intent(this, AlarmReceiver.class),
                    PendingIntent.FLAG_UPDATE_CURRENT);
            mAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        });
    }
}