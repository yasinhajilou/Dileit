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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

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


        mReporterViewModel.getLiveReportsReviewed().observe(getViewLifecycleOwner(), wordReviewHistories -> {
            mHistoryList = wordReviewHistories;
            mCalendar = Calendar.getInstance();
            mCalendar.setTimeInMillis(System.currentTimeMillis());
        });

        mReporterViewModel.getTimeFilterFlag().observe(getViewLifecycleOwner(), integer -> {
            switch (integer) {
                case DAY:
                    int startedHour = mCalendar.get(Calendar.HOUR_OF_DAY);
                    boolean whileCondition = true;
                    int a = 0;
                    final ArrayList<String> xAxisLabel = new ArrayList<>();

                    while (whileCondition) {

                        String reversedHour = String.format("%02d", startedHour--);
                        if (startedHour == 0)
                            startedHour = 24;
                        mMapDayReviewCounter.put(reversedHour, 0);
                        xAxisLabel.add(reversedHour);
                        a++;
                        if (a > 23)
                            whileCondition = false;
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

                    mBinding.barChartReviewed.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));


                    XAxis xAxis = mBinding.barChartReviewed.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setLabelCount(16);

                    BarDataSet set = new BarDataSet(barEntries, "BarDataSet");
                    set.setDrawValues(false);
                    BarData data = new BarData(set);
                    data.setBarWidth(0.9f); // set custom bar width
                    mBinding.barChartReviewed.setData(data);
                    mBinding.barChartReviewed.setScaleEnabled(false);
                    mBinding.barChartReviewed.setFitBars(true); // make the x-axis fit exactly all bars
                    mBinding.barChartReviewed.invalidate();
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
}