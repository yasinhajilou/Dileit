package com.example.dileit.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.example.dileit.view.fragment.TimePickerDialogFragment;
import com.example.dileit.databinding.ActivitySettingBinding;
import com.example.dileit.reciever.AlarmReceiver;

public class SettingActivity extends AppCompatActivity {

    private ActivitySettingBinding mBinding;
    private AlarmManager mAlarmManager;
    private final int REQ_CODE_ALARM_REC = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent alarmIntent = new Intent(this, AlarmReceiver.class);

        if (PendingIntent.getBroadcast(this,
                REQ_CODE_ALARM_REC, alarmIntent, PendingIntent.FLAG_NO_CREATE) != null) {
            mBinding.switchReminder.setChecked(true);
        } else {
            mBinding.switchReminder.setChecked(false);
        }

        mBinding.linearLayoutTimeEditor.setOnClickListener(view -> {
            TimePickerDialogFragment timePickerDialogFragment = new TimePickerDialogFragment();
            timePickerDialogFragment.show(getSupportFragmentManager(), "DIALOG_TIME_PICKER");
        });
    }
}