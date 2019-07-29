package com.example.dileit.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.EditText;

import com.example.dileit.R;
import com.example.dileit.view.fragment.HomeFragment;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpHomeFragment();
    }

    private void setUpHomeFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container_main_activity, new HomeFragment()
                        , HomeFragment.class.getSimpleName())
                .commit();
    }
}
