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
    public void onLeitnerReviewClicked() {
        Intent intent = new Intent(MainActivity.this, ReviewLeitnerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLeitnerManagerClicked() {
        Intent intent = new Intent(MainActivity.this, LeitnerManagerActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mExternalViewModel.closeExternalDbs();
    }
}
