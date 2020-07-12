package com.example.dileit.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dileit.R;
import com.example.dileit.utils.AlarmManagerUtils;
import com.example.dileit.utils.SharedPreferenceUtil;
import com.example.dileit.view.fragment.TimePickerDialogFragment;
import com.example.dileit.databinding.ActivitySettingBinding;
import com.example.dileit.viewmodel.InternalViewModel;
import com.example.dileit.viewmodel.TimeSharedViewModel;

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

        mInternalViewModel = ViewModelProviders.of(this).get(InternalViewModel.class);
        mTimeSharedViewModel = ViewModelProviders.of(this).get(TimeSharedViewModel.class);

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
                }else {

                }
            } else {
                handleTimePickerEnabling(false);
                if (mAlarmManagerUtils.checkForExistingAlarm() != null) {
                    mAlarmManagerUtils.cancelAlarm();
                }
            }
        });
        mBinding.linearLayoutTimeEditor.setOnClickListener(view -> {
            showTimePicker();
        });

        mBinding.btnDeleteHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("Delete History")
                        .setMessage("Are you want to delete Search History?")
                        .setNegativeButton("No", (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        })
                        .setNeutralButton("Yes", (dialogInterface, i) -> {
                            mInternalViewModel.deleteAllHistory();
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        mTimeSharedViewModel.getCancelListener().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                //check for is it first time for setting reminder or not
                //if not we use default values in SP
                if (lastHour == -1 && lastMin == -1) {
                    //we should swicth back because time picking canceled
                    if (aBoolean)
                        mBinding.switchReminder.setChecked(false);
                }
            }
        });
        mInternalViewModel.getDeletedHistoryResult().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                    Toast.makeText(SettingActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(SettingActivity.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });

        mTimeSharedViewModel.getTime().observe(this, ints -> {

            lastHour = ints[0];
            lastMin = ints[1];

            initTextViews(lastHour, lastMin);

            mSharedPreferenceUtil.setTime(lastHour, lastMin);

            mAlarmManagerUtils.setAlarm(lastHour, lastMin);
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