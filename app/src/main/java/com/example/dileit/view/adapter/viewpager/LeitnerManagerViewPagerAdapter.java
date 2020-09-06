package com.example.dileit.view.adapter.viewpager;

import android.content.Context;

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

    private Context mContext;
    public LeitnerManagerViewPagerAdapter(@NonNull FragmentManager fm , Context context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
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
                return mContext.getString(R.string.new_);
            case 1:
                return mContext.getString(R.string.review);
            case 2:
                return  mContext.getString(R.string.learned);
            default:
                return  "null";
        }
    }
}
