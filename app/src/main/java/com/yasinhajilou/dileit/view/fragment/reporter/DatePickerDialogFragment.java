package com.yasinhajilou.dileit.view.fragment.reporter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yasinhajilou.dileit.constant.TimeReporterFilter;
import com.yasinhajilou.dileit.databinding.FragmentDatePickerDialogBinding;
import com.yasinhajilou.dileit.viewmodel.ReporterViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import static com.yasinhajilou.dileit.constant.TimeReporterFilter.DAY;
import static com.yasinhajilou.dileit.constant.TimeReporterFilter.MONTH;
import static com.yasinhajilou.dileit.constant.TimeReporterFilter.WEEK;
import static com.yasinhajilou.dileit.constant.TimeReporterFilter.YEAR;


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

        mReporterViewModel = new ViewModelProvider(getActivity()).get(ReporterViewModel.class);

        mBinding.chipReportDay.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                long today = System.currentTimeMillis();
                long startTime = today -  24 * 60 * 60 * 1000;
                mReporterViewModel.setLiveTimeRange(new long[]{startTime, today});
                mReporterViewModel.setSelectedTime(TimeReporterFilter.DAY);
                mBinding.tvTimeReport.setText(TimeReporterFilter.DAY_SPECIFIER(getContext()));

            }
        });

        mBinding.chipReportWeek.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                long today = System.currentTimeMillis();
                long startTime = today - 7 * 24 * 60 * 60 * 1000;
                mReporterViewModel.setLiveTimeRange(new long[]{startTime, today });
                mReporterViewModel.setSelectedTime(TimeReporterFilter.WEEK);
                mBinding.tvTimeReport.setText(TimeReporterFilter.WEEK_SPECIFIER(getContext()));


            }
        });


        mBinding.chipReportMonth.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                long today = System.currentTimeMillis();
                long startTime = today - 30 * 24 * 60 * 60 * 1000L;
                mReporterViewModel.setLiveTimeRange(new long[]{startTime , today});
                mReporterViewModel.setSelectedTime(TimeReporterFilter.MONTH);
                mBinding.tvTimeReport.setText(TimeReporterFilter.MONTH_SPECIFIER(getContext()));

            }
        });


        mBinding.chipReportYear.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                long today = System.currentTimeMillis();
                long startTime = today - 365 * 24 * 60 * 60 * 1000L;
                mReporterViewModel.setLiveTimeRange(new long[]{startTime , today});
                mReporterViewModel.setSelectedTime(TimeReporterFilter.YEAR);
                mBinding.tvTimeReport.setText(TimeReporterFilter.YEAR_SPECIFIER(getContext()));

            }
        });

        mReporterViewModel.getTimeFilterFlag().observe(getViewLifecycleOwner(), integer -> {
            switch (integer) {
                case DAY:
                    mBinding.chipReportDay.setChecked(true);
                    break;
                case WEEK:
                    mBinding.chipReportWeek.setChecked(true);
                    break;
                case MONTH:
                    mBinding.chipReportMonth.setChecked(true);
                    break;
                case YEAR:
                    mBinding.chipReportYear.setChecked(true);
                    break;
            }
        });
    }
}