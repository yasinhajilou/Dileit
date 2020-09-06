package com.example.dileit.view.fragment.reporter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dileit.R;
import com.example.dileit.databinding.FragmentAddedReporterBinding;
import com.example.dileit.model.LeitnerReport;
import com.example.dileit.model.entity.WordReviewHistory;
import com.example.dileit.viewmodel.ChartsReporterViewModel;
import com.example.dileit.viewmodel.ReporterViewModel;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import saman.zamani.persiandate.PersianDate;

import static com.example.dileit.constant.TimeReporterFilter.DAY;
import static com.example.dileit.constant.TimeReporterFilter.MONTH;
import static com.example.dileit.constant.TimeReporterFilter.WEEK;
import static com.example.dileit.constant.TimeReporterFilter.YEAR;


public class AddedReporterFragment extends Fragment {
    private FragmentAddedReporterBinding mBinding;
    private List<LeitnerReport> mLeitnerReportList;
    private Map<String, Integer> mMapDayReviewCounter = new LinkedHashMap<>();
    private Map<Integer, Integer> mMapWeekReviewCounter = new LinkedHashMap<>();
    private Map<String, Integer> mMapMonthReviewCounter = new LinkedHashMap<>();
    private Map<String, Integer> mMapYearReviewCounter = new LinkedHashMap<>();

    private String TAG = AddedReporterFragment.class.getSimpleName();

    private int selectedTime = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentAddedReporterBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ReporterViewModel reporterViewModel = ViewModelProviders.of(getActivity()).get(ReporterViewModel.class);
        ChartsReporterViewModel chartsReporterViewModel = ViewModelProviders.of(this).get(ChartsReporterViewModel.class);

        mBinding.barChartAdded.setScaleEnabled(false);
        mBinding.barChartAdded.setFitBars(true); // make the x-axis fit exactly all bars

        reporterViewModel.getSelectedTimeRange().observe(getViewLifecycleOwner(), times -> {
            //divide on complete day (24 hour)
            long longTime = (times[1] - times[0]) / (24 * 60 * 60 * 1000);
            int time = (int) longTime;
            switch (time) {
                case 1:
                    selectedTime = DAY;
                    break;
                case 7:
                    selectedTime = WEEK;
                    break;
                case 30:
                    selectedTime = MONTH;
                    break;
                case 365:
                    selectedTime = YEAR;
                    break;
            }

            chartsReporterViewModel.setTimeForAddedList(times);
        });

        chartsReporterViewModel.getLiveListAdded().observe(getViewLifecycleOwner(), leitnerReports -> {
            mLeitnerReportList = leitnerReports;
            switch (selectedTime) {
                case DAY:
                    setUpChartDay();
                    break;
                case WEEK:
                    setUpChartWeek();
                    break;
                case MONTH:
                    setUpChartMonth();
                    break;
                case YEAR:
                    setUpChartYear();
                    break;
            }

        });
    }


    private void setUpChartDay() {
        //get now hour for getting last 24h
        int startedHour = new PersianDate(System.currentTimeMillis()).getHour();

        final ArrayList<String> xAxisLabel = new ArrayList<>();

        for (int i = 0; i < 24; i++) {
            String reversedHour = String.format("%02d", startedHour--);

            if (startedHour == 0)
                startedHour = 24;

            mMapDayReviewCounter.put(reversedHour, 0);
            xAxisLabel.add(reversedHour);
        }

        for (LeitnerReport leitnerReport :
                mLeitnerReportList) {
            PersianDate persianDate = new PersianDate(leitnerReport.getTimeAdded());
            String hour = String.format("%02d", persianDate.getHour());
            int lastCount = mMapDayReviewCounter.get(hour);
            mMapDayReviewCounter.put(hour, ++lastCount);
        }

        float c = 0;

        List<BarEntry> barEntries = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : mMapDayReviewCounter.entrySet()) {
            barEntries.add(new BarEntry(c, entry.getValue()));
            c++;
        }

        XAxis xAxis = mBinding.barChartAdded.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        mBinding.barChartAdded.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
        xAxis.setLabelCount(16);

        BarDataSet set = new BarDataSet(barEntries, " ");
        set.setDrawValues(false);
        set.setColors(ColorTemplate.VORDIPLOM_COLORS);

        BarData data = new BarData(set);
        mBinding.barChartAdded.setData(data);
        mBinding.barChartAdded.animateXY(1000, 1000);

        Description description = new Description();
        description.setText(getString(R.string.last_24_h));
        mBinding.barChartAdded.setDescription(description);
        mBinding.barChartAdded.invalidate();
    }

    private void setUpChartWeek() {

        final ArrayList<String> xAxisLabelWeek = new ArrayList<>();

        int todayIndexOfWeek = new PersianDate(System.currentTimeMillis()).dayOfWeek();

        for (int i = 0; i < 7; i++) {
            String day;
            switch (todayIndexOfWeek) {
                case 0:
                    day = getString(R.string.sat);
                    xAxisLabelWeek.add(day);
                    break;
                case 1:
                    day = getString(R.string.sun);
                    xAxisLabelWeek.add(day);
                    break;
                case 2:
                    day = getString(R.string.mon);
                    xAxisLabelWeek.add(day);
                    break;
                case 3:
                    day = getString(R.string.tue);
                    xAxisLabelWeek.add(day);
                    break;
                case 4:
                    day = getString(R.string.wed);
                    xAxisLabelWeek.add(day);
                    break;
                case 5:
                    day = getString(R.string.thu);
                    xAxisLabelWeek.add(day);
                    break;
                case 6:
                    day = getString(R.string.fri);
                    xAxisLabelWeek.add(day);
                    break;
            }

            mMapWeekReviewCounter.put(todayIndexOfWeek, 0);

            todayIndexOfWeek--;

            if (todayIndexOfWeek < 0)
                todayIndexOfWeek = 6;
        }

        for (LeitnerReport leitnerReport :
                mLeitnerReportList) {
            PersianDate persianDate = new PersianDate(leitnerReport.getTimeAdded());
            int weekIndex = persianDate.dayOfWeek();
            int lastCount = mMapWeekReviewCounter.get(weekIndex);
            mMapWeekReviewCounter.put(weekIndex, ++lastCount);
        }

        List<BarEntry> barEntries = new ArrayList<>();
        int m = 0;
        for (Map.Entry<Integer, Integer> entry : mMapWeekReviewCounter.entrySet()) {
            barEntries.add(new BarEntry(m, entry.getValue()));
            m++;
        }

        XAxis xAxis = mBinding.barChartAdded.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        mBinding.barChartAdded.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabelWeek));
        xAxis.setLabelCount(7);

        BarDataSet setWeek = new BarDataSet(barEntries, " ");
        setWeek.setDrawValues(false);
        setWeek.setColors(ColorTemplate.VORDIPLOM_COLORS);

        BarData dataWeek = new BarData(setWeek);
        mBinding.barChartAdded.setData(dataWeek);
        mBinding.barChartAdded.animateXY(1000, 1000);


        Description descriptionWeek = new Description();
        descriptionWeek.setText(getString(R.string.last_week));
        mBinding.barChartAdded.setDescription(descriptionWeek);
        mBinding.barChartAdded.setFitBars(true);
        mBinding.barChartAdded.invalidate();
    }

    private void setUpChartMonth() {

        final ArrayList<String> xAxisLabelMonth = new ArrayList<>();

        long todayTimeStamp = System.currentTimeMillis();
        PersianDate persianDate = new PersianDate(todayTimeStamp);
        int todayIndexOfMonth = persianDate.getShDay();
        for (int i = 0; i < 30; i++) {
            String day;
            switch (todayIndexOfMonth) {
                case 1:
                    day = getString(R.string.first);
                    xAxisLabelMonth.add(day);
                    mMapMonthReviewCounter.put("1", 0);
                    break;
                case 2:
                    day = getString(R.string.second);
                    xAxisLabelMonth.add(day);
                    mMapMonthReviewCounter.put("2", 0);
                    break;
                case 3:
                    day = getString(R.string.third);
                    xAxisLabelMonth.add(day);
                    mMapMonthReviewCounter.put("3", 0);
                    break;
                default:
                    day = todayIndexOfMonth + getString(R.string.th_date);
                    xAxisLabelMonth.add(day);
                    mMapMonthReviewCounter.put(todayIndexOfMonth + "", 0);
            }


            todayIndexOfMonth--;

            if (todayIndexOfMonth == 0) {
                PersianDate persianDate1 = new PersianDate(todayTimeStamp - 30 * 24 * 60 * 60 * 1000L);
                todayIndexOfMonth = persianDate1.getMonthLength();
            }

        }

        for (LeitnerReport leitnerReport :
                mLeitnerReportList) {
            PersianDate persianDate2 = new PersianDate(leitnerReport.getTimeAdded());
            if (mMapMonthReviewCounter.get(persianDate2.getShDay() + "") != null) {
                int lastCount = mMapMonthReviewCounter.get(persianDate2.getShDay() + "");
                mMapMonthReviewCounter.put(persianDate2.getShDay() + "", ++lastCount);
            }
        }

        List<BarEntry> barEntries = new ArrayList<>();
        int a = 0;
        for (Map.Entry<String, Integer> entry : mMapMonthReviewCounter.entrySet()) {
            barEntries.add(new BarEntry(a++, entry.getValue()));
        }

        XAxis xAxis = mBinding.barChartAdded.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(12);
        mBinding.barChartAdded.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabelMonth));

        BarDataSet setWeek = new BarDataSet(barEntries, " ");
        setWeek.setDrawValues(false);
        setWeek.setColors(ColorTemplate.VORDIPLOM_COLORS);

        BarData dataWeek = new BarData(setWeek);
        mBinding.barChartAdded.setData(dataWeek);
        mBinding.barChartAdded.animateXY(1000, 1000);


        Description descriptionWeek = new Description();
        descriptionWeek.setText(getString(R.string.last_month));
        mBinding.barChartAdded.setDescription(descriptionWeek);
        mBinding.barChartAdded.setFitBars(true);
        mBinding.barChartAdded.invalidate();

    }

    private void setUpChartYear() {

        final ArrayList<String> xAxisLabelYear = new ArrayList<>();

        long todayTimeStamp = System.currentTimeMillis();
        PersianDate persianDate = new PersianDate(todayTimeStamp);
        int todayIndexOfMonths = persianDate.getShMonth();


        for (int i = 0; i < 12; i++) {

            xAxisLabelYear.add(persianDate.monthName(todayIndexOfMonths));
            mMapYearReviewCounter.put(String.valueOf(todayIndexOfMonths), 0);

            todayIndexOfMonths--;
            if (todayIndexOfMonths == 0) {
                todayIndexOfMonths = 12;
            }

        }

        for (
                LeitnerReport leitnerReport :
                mLeitnerReportList) {
            PersianDate persianDate2 = new PersianDate(leitnerReport.getTimeAdded());
            int date = persianDate2.getShMonth();
            int lastCount = mMapYearReviewCounter.get(date + "");
            mMapYearReviewCounter.put(date + "", ++lastCount);

        }

        List<BarEntry> barEntries = new ArrayList<>();
        int a = 0;
        for (
                Map.Entry<String, Integer> entry : mMapYearReviewCounter.entrySet()) {
            barEntries.add(new BarEntry(a, entry.getValue()));
            a++;
        }

        XAxis xAxis = mBinding.barChartAdded.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        mBinding.barChartAdded.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabelYear));
        xAxis.setLabelCount(8);

        BarDataSet setWeek = new BarDataSet(barEntries, " ");
        setWeek.setDrawValues(false);
        setWeek.setColors(ColorTemplate.VORDIPLOM_COLORS);

        BarData dataWeek = new BarData(setWeek);
        mBinding.barChartAdded.setData(dataWeek);
        mBinding.barChartAdded.animateXY(1000, 1000);


        Description descriptionWeek = new Description();
        descriptionWeek.setText(getString(R.string.last_year));
        mBinding.barChartAdded.setDescription(descriptionWeek);
        mBinding.barChartAdded.setFitBars(true);
        mBinding.barChartAdded.invalidate();
    }
}