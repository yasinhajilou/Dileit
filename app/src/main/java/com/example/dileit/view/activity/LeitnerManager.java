package com.example.dileit.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.dileit.R;
import com.example.dileit.databinding.ActivityLeitnerManagerBinding;

public class LeitnerManager extends AppCompatActivity {

    ActivityLeitnerManagerBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLeitnerManagerBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
    }
}
