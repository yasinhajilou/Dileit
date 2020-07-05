package com.example.dileit.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.animation.ValueAnimator;
import android.os.Bundle;

import com.example.dileit.databinding.ActivityReporterBinding;
import com.example.dileit.viewmodel.ReporterViewModel;

public class ReporterActivity extends AppCompatActivity {

    private ActivityReporterBinding mBinding;
    private long duration = 1000;
    private ReporterViewModel mReporterViewModel;

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


        mReporterViewModel.getAllLeitnerCardCount().observe(this, this::showAllCountessWithAnimation);

        mReporterViewModel.getLearnedCardsCount().observe(this, this::showLearnedCountessWithAnimation);


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