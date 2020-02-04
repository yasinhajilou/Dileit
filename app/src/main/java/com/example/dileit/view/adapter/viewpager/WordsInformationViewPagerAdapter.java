package com.example.dileit.view.adapter.viewpager;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class WordsInformationViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String TAG = WordsInformationViewPagerAdapter.class.getSimpleName();

    public WordsInformationViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT );
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        Log.d(TAG, "WordsInformationViewPagerAdapter: " + mFragments.size());
        return mFragments.size();
    }

    public void addPage(Fragment fragment) {
        mFragments.add(fragment);
        notifyDataSetChanged();
    }
}
