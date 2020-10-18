package com.yasinhajilou.dileit.view.adapter.viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.yasinhajilou.dileit.view.fragment.reporter.AddedReporterFragment;
import com.yasinhajilou.dileit.view.fragment.reporter.ReviewedReporterFragment;

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
