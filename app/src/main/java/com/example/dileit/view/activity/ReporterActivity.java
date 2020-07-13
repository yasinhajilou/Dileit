package com.example.dileit.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.dileit.constant.TimeReporterFilter;
import com.example.dileit.view.adapter.viewpager.BarChartViewPagerAdapter;
import com.example.dileit.view.fragment.reporter.DatePickerDialogFragment;
import com.example.dileit.databinding.ActivityReporterBinding;
import com.example.dileit.viewmodel.ReporterViewModel;

import static com.example.dileit.constant.TimeReporterFilter.DAY;
import static com.example.dileit.constant.TimeReporterFilter.MONTH;
import static com.example.dileit.constant.TimeReporterFilter.WEEK;
import static com.example.dileit.constant.TimeReporterFilter.YEAR;

public class ReporterActivity extends AppCompatActivity {

    private ActivityReporterBinding mBinding;
    private long duration = 1000;
    private ReporterViewModel mReporterViewModel;

    private String TAG = ReporterActivity.class.getSimpleName();
    private int allLeitnerWord;
    private int allReviewedCards;
    private BarChartViewPagerAdapter mBarChartViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityReporterBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        setSupportActionBar(mBinding.toolbarReporter);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mReporterViewModel = ViewModelProviders.of(this).get(ReporterViewModel.class);

        mBarChartViewPagerAdapter = new BarChartViewPagerAdapter(getSupportFragmentManager());

        mBinding.vpBarChart.setAdapter(mBarChartViewPagerAdapter);

        mReporterViewModel.getAllLeitnerCardCount().observe(this, integer -> {
            showAllCountessWithAnimation(integer);
            allLeitnerWord = integer;

            //setting data up
            mReporterViewModel.setLiveTimeRange(new long[]{30 * 24 * 60 * 60 * 1000L, System.currentTimeMillis()});
            mReporterViewModel.setSelectedTime(TimeReporterFilter.MONTH);
        });

        mReporterViewModel.getWordReviewedHistoryCount().observe(this, count -> {
            allReviewedCards = count;
        });

        mReporterViewModel.getLearnedCardsCount().observe(this, this::showLearnedCountessWithAnimation);

        mReporterViewModel.getLiveReportAdded().observe(this, leitnerReports -> {
            int size = leitnerReports.size();
            Log.d(TAG, "onCreate: " + size);
            mBinding.cpAdded.setProgress(size, allLeitnerWord);
        });

        mReporterViewModel.getLiveReportsReviewed().observe(this, leitnerReports -> {
            int size = leitnerReports.size();
            mBinding.cpReviewed.setProgress(size, allReviewedCards);
        });

        mReporterViewModel.getTimeFilterFlag().observe(this, integer -> {
            Toast.makeText(this, "" + integer, Toast.LENGTH_SHORT).show();
        });

        mBinding.chipHeadFilter.setOnClickListener(view -> {
            DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
            datePickerDialogFragment.show(getSupportFragmentManager(), "DatePickerDialogFragment");
        });

        mReporterViewModel.getTimeFilterFlag().observe( this , integer -> {
            switch (integer) {
                case DAY:
                    mBinding.chipHeadFilter.setText("Day");
                    break;
                case WEEK:
                    mBinding.chipHeadFilter.setText("Week");
                    break;
                case MONTH:
                    mBinding.chipHeadFilter.setText("Month");
                    break;
                case YEAR:
                    mBinding.chipHeadFilter.setText("Year");
                    break;
            }
        });

    }

    private void showAllCountessWithAnimation(int allCardsCount) {
        ValueAnimator animator = ValueAnimator.ofInt(0, allCardsCount);
        animator.setDuration(duration);
        animator.addUpdateListener(valueAnimator -> mBinding.tvTotalCardsCount.setText(valueAnimator.getAnimatedValue().toString()));

        animator.start();
    }

    private void showLearnedCountessWithAnimation(int learnedCards) {
        ValueAnimator animator = ValueAnimator.ofInt(0, learnedCards);
        animator.setDuration(duration);
        animator.addUpdateListener(valueAnimator -> mBinding.tvLearnedCardsCount.setText(valueAnimator.getAnimatedValue().toString()));

        animator.start();
    }
}