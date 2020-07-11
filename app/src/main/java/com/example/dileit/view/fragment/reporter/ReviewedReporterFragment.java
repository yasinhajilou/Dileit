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
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    private int nowHour;

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
            mCalendar = Calendar.getInstance();
            mCalendar.setTimeInMillis(System.currentTimeMillis());

        });

        mReporterViewModel.getTimeFilterFlag().observe(getViewLifecycleOwner(), integer -> {
            switch (integer) {
                case DAY:
                    setUpChartDay();
                    break;
                case WEEK:
                    break;
                case MONTH:
                    break;
                case YEAR:
                    break;
            }
        });
    }

    private void setUpChartDay() {
        //get now hour for getting last 24h
        int startedHour = mCalendar.get(Calendar.HOUR_OF_DAY);

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

        Description description = new Description();
        description.setText("Last 24 hours");
        mBinding.barChartReviewed.setDescription(description);
        mBinding.barChartReviewed.invalidate();
    }

    private void setUpChartWeek() {

        final ArrayList<String> xAxisLabelWeek = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            String day;
            switch (i) {
                case 1:
                    day = "Sat";
                    xAxisLabelWeek.add(day);
                    break;
                case 2:
                    day = "Sun";
                    xAxisLabelWeek.add(day);
                    break;
                case 3:
                    day = "Mon";
                    xAxisLabelWeek.add(day);
                    break;
                case 4:
                    day = "Tue";
                    xAxisLabelWeek.add(day);
                    break;
                case 5:
                    day = "Wed";
                    xAxisLabelWeek.add(day);
                    break;
                case 6:
                    day = "Thu";
                    xAxisLabelWeek.add(day);
                    break;
                case 7:
                    day = "Fri";
                    xAxisLabelWeek.add(day);
                    break;
            }

            mMapWeekReviewCounter.put(i, 0);
        }

        for (WordReviewHistory wordReviewHistory :
                mHistoryList) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(wordReviewHistory.getReviewedTime());
            int lastCount = mMapWeekReviewCounter.get(calendar.get(Calendar.DAY_OF_WEEK));
            mMapWeekReviewCounter.put(calendar.get(Calendar.DAY_OF_WEEK), ++lastCount);
        }

        List<BarEntry> barEntries = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : mMapWeekReviewCounter.entrySet()) {
            barEntries.add(new BarEntry(entry.getKey(), entry.getValue()));
        }

        XAxis xAxis = mBinding.barChartReviewed.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        mBinding.barChartReviewed.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabelWeek));
        xAxis.setLabelCount(8);

        BarDataSet setWeek = new BarDataSet(barEntries, " ");
        setWeek.setDrawValues(false);
        setWeek.setColors(ColorTemplate.VORDIPLOM_COLORS);

        BarData dataWeek = new BarData(setWeek);
        mBinding.barChartReviewed.setData(dataWeek);

        Description descriptionWeek = new Description();
        descriptionWeek.setText("Last Week");
        mBinding.barChartReviewed.setDescription(descriptionWeek);
        mBinding.barChartReviewed.setFitBars(true);
        mBinding.barChartReviewed.invalidate();
    }
}