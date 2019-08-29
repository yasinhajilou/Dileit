package com.example.dileit.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dileit.R;
import com.example.dileit.view.fragment.HomeFragment;
import com.example.dileit.view.fragment.WordSearchFragment;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
