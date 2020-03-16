package com.example.dileit.view.adapter.viewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.dileit.view.fragment.wordinfo.leitnersetup.TranslationDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class AddNewLeitnerViewPagerAdapter extends FragmentPagerAdapter {
    List<String> titles = new ArrayList<>();
    public AddNewLeitnerViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TranslationDialogFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
