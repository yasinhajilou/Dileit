package com.example.dileit.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.dileit.R;
import com.example.dileit.databinding.ActivityReviewLeitnerBinding;
import com.example.dileit.view.adapter.viewpager.LeitnerReviewViewPagerAdapter;

public class ReviewLeitnerActivity extends AppCompatActivity {

    LeitnerReviewViewPagerAdapter mAdapter;
    ActivityReviewLeitnerBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_leitner);

        mBinding = ActivityReviewLeitnerBinding.inflate(getLayoutInflater());
        mAdapter = new LeitnerReviewViewPagerAdapter(getSupportFragmentManager());

    }
}
