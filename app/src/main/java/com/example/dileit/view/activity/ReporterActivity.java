package com.example.dileit.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.dileit.model.entity.WordReviewHistory;
import com.example.dileit.view.adapter.viewpager.BarChartViewPagerAdapter;
import com.example.dileit.view.fragment.DatePickerDialogFragment;
import com.example.dileit.databinding.ActivityReporterBinding;
import com.example.dileit.viewmodel.ReporterViewModel;

import java.util.Calendar;

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
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -30);
            mReporterViewModel.setLiveTimeRange(new long[]{calendar.getTimeInMillis(), System.currentTimeMillis()});
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
            Log.d(TAG, "onCreate: " + size);
            mBinding.cpReviewed.setProgress(size, allReviewedCards);
        });

        mReporterViewModel.getTimeFilterFlag().observe(this, integer -> {
            Toast.makeText(this, "" + integer, Toast.LENGTH_SHORT).show();
        });

        mBinding.chipHeadFilter.setOnClickListener(view -> {
            DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
            datePickerDialogFragment.show(getSupportFragmentManager(), "DatePickerDialogFragment");
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