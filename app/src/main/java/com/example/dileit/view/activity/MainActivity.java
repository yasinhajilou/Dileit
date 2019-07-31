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

        setUpHomeFragment();
    }

    private void setUpHomeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(HomeFragment.class.getSimpleName());
        if (fragment==null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container_main_activity,  HomeFragment.newInstance()
                            , HomeFragment.class.getSimpleName())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1)
            getFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }
}
