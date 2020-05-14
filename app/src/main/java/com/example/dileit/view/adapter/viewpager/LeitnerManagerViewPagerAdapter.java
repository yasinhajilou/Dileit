package com.example.dileit.view.adapter.viewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.dileit.R;
import com.example.dileit.view.fragment.leitnermanager.LearnedWordsManagerFragment;
import com.example.dileit.view.fragment.leitnermanager.NewWordsManagerFragment;
import com.example.dileit.view.fragment.leitnermanager.ReviewWordsManagerFragment;

public class LeitnerManagerViewPagerAdapter extends FragmentStatePagerAdapter {


    public LeitnerManagerViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new NewWordsManagerFragment();
            case 1:
                return new ReviewWordsManagerFragment();
            case 2:
                return new LearnedWordsManagerFragment();
            default:
                return new NewWordsManagerFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "New";
            case 1:
                return "Review";
            case 2:
                return  "Learned";
            default:
                return  "New";
        }
    }
}
