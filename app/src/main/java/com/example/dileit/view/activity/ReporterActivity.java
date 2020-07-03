package com.example.dileit.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;

import com.db.williamchart.data.Frame;
import com.example.dileit.databinding.ActivityReporterBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReporterActivity extends AppCompatActivity {

    private ActivityReporterBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityReporterBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());


    }
}