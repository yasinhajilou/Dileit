package com.example.dileit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dileit.databinding.FragmentDatePickerDialogBinding;
import com.example.dileit.viewmodel.ReporterViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DatePickerDialogFragment extends BottomSheetDialogFragment {
    private ReporterViewModel mReporterViewModel;
    private FragmentDatePickerDialogBinding mBinding;
    private String TAG = DatePickerDialogFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentDatePickerDialogBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mReporterViewModel = ViewModelProviders.of(getActivity()).get(ReporterViewModel.class);

        mBinding.chipReportDay.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR_OF_DAY, -24);
                mReporterViewModel.setTimeRange(new long[]{calendar.getTimeInMillis(), System.currentTimeMillis()});
            }
        });

        mBinding.chipReportWeek.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, -7);
                mReporterViewModel.setTimeRange(new long[]{calendar.getTimeInMillis(), System.currentTimeMillis()});

            }
        });


        mBinding.chipReportMonth.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH, -1);
                mReporterViewModel.setTimeRange(new long[]{calendar.getTimeInMillis(), System.currentTimeMillis()});

            }
        });


        mBinding.chipReportYear.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.YEAR, -1);
                mReporterViewModel.setTimeRange(new long[]{calendar.getTimeInMillis(), System.currentTimeMillis()});
            }
        });
    }
}