package com.example.dileit.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.dileit.R;
import com.example.dileit.view.fragment.HomeFragment;
import com.google.firebase.analytics.FirebaseAnalytics;


public class MainActivity extends AppCompatActivity implements HomeFragment.OnReviewLeitnerInterface {
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
    public void nextActivity() {
        Intent intent = new Intent(MainActivity.this , ReviewLeitnerActivity.class);
        startActivity(intent);
    }
}
