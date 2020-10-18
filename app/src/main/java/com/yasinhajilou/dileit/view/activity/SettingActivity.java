package com.yasinhajilou.dileit.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.yasinhajilou.dileit.R;
import com.yasinhajilou.dileit.utils.AlarmManagerUtils;
import com.yasinhajilou.dileit.utils.SharedPreferenceUtil;
import com.yasinhajilou.dileit.view.fragment.TimePickerDialogFragment;
import com.yasinhajilou.dileit.databinding.ActivitySettingBinding;
import com.yasinhajilou.dileit.viewmodel.InternalViewModel;
import com.yasinhajilou.dileit.viewmodel.TimeSharedViewModel;

public class SettingActivity extends AppCompatActivity {

    private InternalViewModel mInternalViewModel;
    private TimeSharedViewModel mTimeSharedViewModel;
    private ActivitySettingBinding mBinding;
    private String TAG = SettingActivity.class.getSimpleName();
    private int lastHour, lastMin;
    private SharedPreferenceUtil mSharedPreferenceUtil;
    private AlarmManagerUtils mAlarmManagerUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.toolbarSetting.setTitle(getString(R.string.setting));
        setSupportActionBar(mBinding.toolbarSetting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mSharedPreferenceUtil = new SharedPreferenceUtil(this);

        mAlarmManagerUtils = new AlarmManagerUtils(this);

        mInternalViewModel = new ViewModelProvider(this).get(InternalViewModel.class);
        mTimeSharedViewModel = new ViewModelProvider(this).get(TimeSharedViewModel.class);

        lastHour = mSharedPreferenceUtil.getHour();
        lastMin = mSharedPreferenceUtil.getMin();
        if (lastHour != -1 && lastMin != -1) {
            initTextViews(lastHour, lastMin);
        }

        if (mAlarmManagerUtils.checkForExistingAlarm() != null) {
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
                } else {
                    mAlarmManagerUtils.setAlarm(lastHour, lastMin);
                    mSharedPreferenceUtil.setTime(lastHour, lastMin);
                    mSharedPreferenceUtil.setAlarmManagerStatus(true);
                    initTextViews(lastHour, lastMin);
                    Toast.makeText(this, getString(R.string.alarm_cap) + lastHour + ":" + lastMin, Toast.LENGTH_LONG).show();
                }
            } else {
                handleTimePickerEnabling(false);
                if (mAlarmManagerUtils.checkForExistingAlarm() != null) {
                    mAlarmManagerUtils.cancelAlarm();
                    mSharedPreferenceUtil.setAlarmManagerStatus(false);
                }
            }
        });

        mBinding.linearLayoutTimeEditor.setOnClickListener(view -> {
            showTimePicker();
        });

        mBinding.btnDeleteHistory.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this)
                    .setTitle(R.string.delete_his)
                    .setMessage(R.string.delete_his_cap)
                    .setNegativeButton(getString(R.string.no), (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    })
                    .setNeutralButton(getString(R.string.yes), (dialogInterface, i) -> {
                        mInternalViewModel.deleteAllHistory();
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        mTimeSharedViewModel.getCancelListener().observe(this, aBoolean -> {

            //check for is it first time for setting reminder or not
            //if not we use default values in SP
            if (lastHour == -1 && lastMin == -1) {
                //we should swicth back because time picking canceled
                if (aBoolean)
                    mBinding.switchReminder.setChecked(false);
            }
        });

        mInternalViewModel.getDeletedHistoryResult().observe(this, aBoolean -> {
            if (aBoolean)
                Toast.makeText(SettingActivity.this, R.string.deleted_successfully, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(SettingActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
        });

        mTimeSharedViewModel.getTime().observe(this, ints -> {

            lastHour = ints[0];
            lastMin = ints[1];

            initTextViews(lastHour, lastMin);

            mSharedPreferenceUtil.setTime(lastHour, lastMin);
            mSharedPreferenceUtil.setAlarmManagerStatus(true);
            mAlarmManagerUtils.setAlarm(lastHour, lastMin);
            String hour = String.format("%02d", lastHour);
            String min = String.format("%02d", lastMin);
            Toast.makeText(this, R.string.alarm_cap + hour + ":" + min , Toast.LENGTH_LONG).show();

        });
    }

    private void handleTimePickerEnabling(boolean b) {
        mBinding.tvReminderH.setEnabled(b);
        mBinding.tvReminderM.setEnabled(b);
        mBinding.linearLayoutTimeEditor.setEnabled(b);
    }


    private void initTextViews(int h, int m) {
        mBinding.tvReminderH.setText(String.format("%02d", h));
        mBinding.tvReminderM.setText(String.format("%02d", m));
    }

    private void showTimePicker() {
        TimePickerDialogFragment timePickerDialogFragment = new TimePickerDialogFragment();
        timePickerDialogFragment.show(getSupportFragmentManager(), "DIALOG_TIME_PICKER");
    }
}