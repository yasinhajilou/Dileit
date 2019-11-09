package com.example.dileit.view.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class WordsInformationViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<String> mTitles = new ArrayList<>();
    public WordsInformationViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    public void addPage(Fragment fragment , String title){
        mFragments.add(fragment);
        mTitles.add(title);
        notifyDataSetChanged();
    }
}
