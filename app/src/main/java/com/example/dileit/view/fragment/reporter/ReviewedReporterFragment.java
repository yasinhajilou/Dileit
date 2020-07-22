package com.example.dileit.view.fragment.reporter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dileit.R;
import com.example.dileit.databinding.FragmentRelatedIdiomsBinding;
import com.example.dileit.databinding.FragmentReviewedReporterBinding;
import com.example.dileit.model.entity.WordReviewHistory;
import com.example.dileit.viewmodel.ReporterViewModel;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import saman.zamani.persiandate.PersianDate;

import static com.example.dileit.constant.TimeReporterFilter.DAY;
import static com.example.dileit.constant.TimeReporterFilter.WEEK;
import static com.example.dileit.constant.TimeReporterFilter.MONTH;
import static com.example.dileit.constant.TimeReporterFilter.YEAR;


public class ReviewedReporterFragment extends Fragment {

    private FragmentReviewedReporterBinding mBinding;
    private ReporterViewModel mReporterViewModel;
    private List<WordReviewHistory> mHistoryList;
    private Calendar mCalendar;
    private String TAG = ReviewedReporterFragment.class.getSimpleName();

    private Map<String, Integer> mMapDayReviewCounter = new LinkedHashMap<>();
    private Map<Integer, Integer> mMapWeekReviewCounter = new LinkedHashMap<>();
    private Map<String, Integer> mMapMonthReviewCounter = new LinkedHashMap<>();
    private Map<String, Integer> mMapYearReviewCounter = new LinkedHashMap<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentReviewedReporterBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mReporterViewModel = ViewModelProviders.of(getActivity()).get(ReporterViewModel.class);

        mBinding.barChartReviewed.setScaleEnabled(false);
        mBinding.barChartReviewed.setFitBars(true); // make the x-axis fit exactly all bars

        mReporterViewModel.getLiveReportsReviewed().observe(getViewLifecycleOwner(), wordReviewHistories -> {
            mHistoryList = wordReviewHistories;
        });

        mReporterViewModel.getLiveSyncedTimeLists().observe(getViewLifecycleOwner(), integer -> {
            switch (integer) {
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

        for (WordReviewHistory wordReviewHistory :
                mHistoryList) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(wordReviewHistory.getReviewedTime());
            String hour = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY));
            int lastCount = mMapDayReviewCounter.get(hour);
            mMapDayReviewCounter.put(hour, ++lastCount);
        }

        float c = 0;

        List<BarEntry> barEntries = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : mMapDayReviewCounter.entrySet()) {
            barEntries.add(new BarEntry(c, entry.getValue()));
            c++;
        }

        XAxis xAxis = mBinding.barChartReviewed.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        mBinding.barChartReviewed.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
        xAxis.setLabelCount(16);

        BarDataSet set = new BarDataSet(barEntries, " ");
        set.setDrawValues(false);
        set.setColors(ColorTemplate.VORDIPLOM_COLORS);

        BarData data = new BarData(set);
        mBinding.barChartReviewed.setData(data);
        mBinding.barChartReviewed.animateXY(1000, 1000);

        Description description = new Description();
        description.setText("Last 24 hours");
        mBinding.barChartReviewed.setDescription(description);
        mBinding.barChartReviewed.invalidate();
    }

    private void setUpChartWeek() {

        final ArrayList<String> xAxisLabelWeek = new ArrayList<>();

        int todayIndexOfWeek = new PersianDate(System.currentTimeMillis()).dayOfWeek();

        for (int i = 0; i < 7; i++) {
            String day;
            switch (todayIndexOfWeek) {
                case 0:
                    day = "Sat";
                    xAxisLabelWeek.add(day);
                    break;
                case 1:
                    day = "Sun";
                    xAxisLabelWeek.add(day);
                    break;
                case 2:
                    day = "Mon";
                    xAxisLabelWeek.add(day);
                    break;
                case 3:
                    day = "Tue";
                    xAxisLabelWeek.add(day);
                    break;
                case 4:
                    day = "Wed";
                    xAxisLabelWeek.add(day);
                    break;
                case 5:
                    day = "Thu";
                    xAxisLabelWeek.add(day);
                    break;
                case 6:
                    day = "Fri";
                    xAxisLabelWeek.add(day);
                    break;
            }

            mMapWeekReviewCounter.put(todayIndexOfWeek, 0);

            todayIndexOfWeek--;

            if (todayIndexOfWeek < 0)
                todayIndexOfWeek = 6;
        }

        for (WordReviewHistory wordReviewHistory :
                mHistoryList) {
            PersianDate persianDate = new PersianDate(wordReviewHistory.getReviewedTime());
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

        XAxis xAxis = mBinding.barChartReviewed.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        mBinding.barChartReviewed.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabelWeek));
        xAxis.setLabelCount(7);

        BarDataSet setWeek = new BarDataSet(barEntries, " ");
        setWeek.setDrawValues(false);
        setWeek.setColors(ColorTemplate.VORDIPLOM_COLORS);

        BarData dataWeek = new BarData(setWeek);
        mBinding.barChartReviewed.setData(dataWeek);
        mBinding.barChartReviewed.animateXY(1000, 1000);


        Description descriptionWeek = new Description();
        descriptionWeek.setText("Last Week");
        mBinding.barChartReviewed.setDescription(descriptionWeek);
        mBinding.barChartReviewed.setFitBars(true);
        mBinding.barChartReviewed.invalidate();
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
                    day = "1st";
                    xAxisLabelMonth.add(day);
                    mMapMonthReviewCounter.put("1", 0);
                    break;
                case 2:
                    day = "2nd";
                    xAxisLabelMonth.add(day);
                    mMapMonthReviewCounter.put("2", 0);
                    break;
                case 3:
                    day = "3rd";
                    xAxisLabelMonth.add(day);
                    mMapMonthReviewCounter.put("3", 0);
                    break;
                default:
                    day = todayIndexOfMonth + "th";
                    xAxisLabelMonth.add(day);
                    mMapMonthReviewCounter.put(todayIndexOfMonth + "", 0);
            }


            todayIndexOfMonth--;

            if (todayIndexOfMonth < 0) {
                PersianDate persianDate1 = new PersianDate(todayTimeStamp - 30 * 24 * 60 * 60 * 1000L);
                todayIndexOfMonth = persianDate1.getMonthLength();
            }

        }

        for (WordReviewHistory wordReviewHistory :
                mHistoryList) {
            PersianDate persianDate2 = new PersianDate(wordReviewHistory.getReviewedTime());
            int lastCount = mMapMonthReviewCounter.get(persianDate2.getShDay() + "");
            mMapMonthReviewCounter.put(persianDate2.getShDay() + "", ++lastCount);
        }

        List<BarEntry> barEntries = new ArrayList<>();
        int a = 0;
        for (Map.Entry<String, Integer> entry : mMapMonthReviewCounter.entrySet()) {
            barEntries.add(new BarEntry(a++, entry.getValue()));
        }

        XAxis xAxis = mBinding.barChartReviewed.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(12);
        mBinding.barChartReviewed.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabelMonth));

        BarDataSet setWeek = new BarDataSet(barEntries, " ");
        setWeek.setDrawValues(false);
        setWeek.setColors(ColorTemplate.VORDIPLOM_COLORS);

        BarData dataWeek = new BarData(setWeek);
        mBinding.barChartReviewed.setData(dataWeek);
        mBinding.barChartReviewed.animateXY(1000, 1000);


        Description descriptionWeek = new Description();
        descriptionWeek.setText("Last 30 days");
        mBinding.barChartReviewed.setDescription(descriptionWeek);
        mBinding.barChartReviewed.setFitBars(true);
        mBinding.barChartReviewed.invalidate();

    }

    private void setUpChartYear() {

        final ArrayList<String> xAxisLabelYear = new ArrayList<>();

        long todayTimeStamp = System.currentTimeMillis();
        PersianDate persianDate = new PersianDate(todayTimeStamp);
        int todayIndexOfMonths = persianDate.getShMonth();

        Log.d(TAG, "setUpChartMonth: " + todayIndexOfMonths);

        for (int i = 0; i < 12; i++) {
            Log.d(TAG, "setUpChartYear: month name " + todayIndexOfMonths + " - " + persianDate.monthName(todayIndexOfMonths));

            xAxisLabelYear.add(persianDate.monthName(todayIndexOfMonths));
            mMapYearReviewCounter.put(String.valueOf(todayIndexOfMonths), 0);

            todayIndexOfMonths--;
            if (todayIndexOfMonths == 0) {
                todayIndexOfMonths = 12;
            }

        }

        for (
                WordReviewHistory wordReviewHistory :
                mHistoryList) {
            PersianDate persianDate2 = new PersianDate(wordReviewHistory.getReviewedTime());
            int date = persianDate2.getShMonth() ;
            int lastCount = mMapYearReviewCounter.get(date+ "");
            mMapYearReviewCounter.put( date +"", ++lastCount);
            Log.d(TAG, "setUpChartYear Reviewed: " + persianDate2.getShMonth() + "--" + lastCount);
            Log.d(TAG, "setUpChartYear: " + xAxisLabelYear.get(date));

        }

        List<BarEntry> barEntries = new ArrayList<>();
        int a = 0 ;
        for (
                Map.Entry<String, Integer> entry : mMapYearReviewCounter.entrySet()) {
            barEntries.add(new BarEntry(a, entry.getValue()));
            a++;
        }

        XAxis xAxis = mBinding.barChartReviewed.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        mBinding.barChartReviewed.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabelYear));
        xAxis.setLabelCount(8);

        BarDataSet setWeek = new BarDataSet(barEntries, " ");
        setWeek.setDrawValues(false);
        setWeek.setColors(ColorTemplate.VORDIPLOM_COLORS);

        BarData dataWeek = new BarData(setWeek);
        mBinding.barChartReviewed.setData(dataWeek);
        mBinding.barChartReviewed.animateXY(1000, 1000);


        Description descriptionWeek = new Description();
        descriptionWeek.setText("Last 12 month");
        mBinding.barChartReviewed.setDescription(descriptionWeek);
        mBinding.barChartReviewed.setFitBars(true);
        mBinding.barChartReviewed.invalidate();
    }


}