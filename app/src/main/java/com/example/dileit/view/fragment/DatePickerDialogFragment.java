package com.example.dileit.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dileit.constant.TimeReporterFilter;
import com.example.dileit.databinding.FragmentDatePickerDialogBinding;
import com.example.dileit.viewmodel.ReporterViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

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
                mReporterViewModel.setLiveTimeRange(new long[]{calendar.getTimeInMillis(), System.currentTimeMillis()});
                mReporterViewModel.setTimeFilter(TimeReporterFilter.DAY);

            }
        });

        mBinding.chipReportWeek.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, -7);
                mReporterViewModel.setLiveTimeRange(new long[]{calendar.getTimeInMillis(), System.currentTimeMillis()});
                mReporterViewModel.setTimeFilter(TimeReporterFilter.WEEK);

            }
        });


        mBinding.chipReportMonth.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, -30);
                mReporterViewModel.setLiveTimeRange(new long[]{calendar.getTimeInMillis(), System.currentTimeMillis()});
                mReporterViewModel.setTimeFilter(TimeReporterFilter.MONTH);
            }
        });


        mBinding.chipReportYear.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.YEAR, -1);
                mReporterViewModel.setLiveTimeRange(new long[]{calendar.getTimeInMillis(), System.currentTimeMillis()});
                mReporterViewModel.setTimeFilter(TimeReporterFilter.YEAR);

            }
        });
    }
}