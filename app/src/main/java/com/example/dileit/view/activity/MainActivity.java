package com.example.dileit.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import com.example.dileit.R;
import com.example.dileit.view.fragment.HomeFragment;
import com.example.dileit.viewmodel.ExternalViewModel;
import com.google.firebase.analytics.FirebaseAnalytics;


public class MainActivity extends AppCompatActivity implements HomeFragment.HomeFragmentInterface {
    private FirebaseAnalytics mFirebaseAnalytics;
    private ExternalViewModel mExternalViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mExternalViewModel = ViewModelProviders.of(this).get(ExternalViewModel.class);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    @Override
    public void onLeitnerReviewButtonTouched() {
        Intent intent = new Intent(MainActivity.this, ReviewLeitnerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSettingMenuTouched() {
        Intent intent = new Intent(MainActivity.this , SettingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLeitnerManagerMenuTouched() {
        Intent intent = new Intent(MainActivity.this, LeitnerManagerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onReporterMenuTouched() {
        Intent intent = new Intent(MainActivity.this, ReporterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mExternalViewModel.closeExternalDbs();
    }
}
