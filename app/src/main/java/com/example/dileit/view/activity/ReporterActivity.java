package com.example.dileit.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.animation.Animation;

import com.db.williamchart.data.Frame;
import com.example.dileit.databinding.ActivityReporterBinding;
import com.example.dileit.viewmodel.InternalViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReporterActivity extends AppCompatActivity {

    private ActivityReporterBinding mBinding;
    private long duration = 1000;
    private InternalViewModel mInternalViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityReporterBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        setSupportActionBar(mBinding.toolbarReporter);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mInternalViewModel = ViewModelProviders.of(this).get(InternalViewModel.class);


        mInternalViewModel.getAllLeitnerCardCount().observe(this, this::showAllCountessWithAnimation);

        mInternalViewModel.getLearnedCardsCount().observe(this, this::showLearnedCountessWithAnimation);


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