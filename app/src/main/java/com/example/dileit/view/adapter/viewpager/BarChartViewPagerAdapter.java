package com.example.dileit.view.adapter.viewpager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.PluralsRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.example.dileit.R;
import com.example.dileit.model.BarChartInfo;
import com.example.dileit.model.LeitnerReport;
import com.example.dileit.view.fragment.reporter.AddedReporterFragment;
import com.example.dileit.view.fragment.reporter.ReviewedReporterFragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;

import java.util.List;

public class BarChartViewPagerAdapter extends FragmentPagerAdapter {

    public BarChartViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new AddedReporterFragment();
            case 0:
            default:
                return new ReviewedReporterFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
