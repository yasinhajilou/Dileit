package com.example.dileit.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.dileit.R;
import com.example.dileit.databinding.ActivityLeitnerManagerBinding;
import com.example.dileit.view.adapter.viewpager.LeitnerManagerViewPagerAdapter;

public class LeitnerManagerActivity extends AppCompatActivity {

    ActivityLeitnerManagerBinding mBinding;
    LeitnerManagerViewPagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLeitnerManagerBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        setSupportActionBar(mBinding.toolbarLeitnerManager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mAdapter = new LeitnerManagerViewPagerAdapter(getSupportFragmentManager());

        mBinding.viewPagerLeitnerManager.setAdapter(mAdapter);
        mBinding.tabLeitnerManager.setupWithViewPager(mBinding.viewPagerLeitnerManager);

    }
}
