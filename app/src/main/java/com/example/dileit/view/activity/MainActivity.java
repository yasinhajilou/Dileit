package com.example.dileit.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.dileit.R;
import com.example.dileit.view.fragment.HomeFragment;
import com.google.firebase.analytics.FirebaseAnalytics;


public class MainActivity extends AppCompatActivity implements HomeFragment.HomeFragmentInterface{
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    @Override
    public void onLeitnerReviewClicked() {
        Intent intent = new Intent(MainActivity.this , ReviewLeitnerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLeitnerManagerClicked() {
        Intent intent = new Intent(MainActivity.this , LeitnerManager.class);
        startActivity(intent);
    }
}
