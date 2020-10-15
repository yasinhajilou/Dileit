package com.example.dileit.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import com.example.dileit.R;
import com.example.dileit.model.database.InternalRoomDatabase;
import com.example.dileit.view.fragment.HomeFragment;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.File;

import ir.androidexception.roomdatabasebackupandrestore.Backup;
import ir.androidexception.roomdatabasebackupandrestore.OnWorkFinishListener;


public class MainActivity extends AppCompatActivity implements HomeFragment.HomeFragmentInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //remove notification if is available
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

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
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
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
    }
}
