package com.example.dileit.view.adapter.viewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.dileit.view.fragment.wordinfo.leitnersetup.SecondTranslationDialogFragment;
import com.example.dileit.view.fragment.wordinfo.leitnersetup.TranslationDialogFragment;

public class AddNewLeitnerViewPager extends FragmentPagerAdapter {
    String[] titles = new String[]{"Translation" , "EnglishTranslation"};

    public AddNewLeitnerViewPager(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment =  new TranslationDialogFragment();
                break;
            case 1:
                fragment =  new SecondTranslationDialogFragment();
                break;
            default:
                fragment = new TranslationDialogFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
