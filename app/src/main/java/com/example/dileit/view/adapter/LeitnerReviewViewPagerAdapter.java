package com.example.dileit.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class LeitnerReviewViewPagerAdapter extends FragmentStateAdapter {

    List<Fragment> mFragments = new ArrayList<>();
    public LeitnerReviewViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragments.size();
    }

    public void addPage(Fragment fragment){
        mFragments.add(fragment);
        notifyDataSetChanged();
    }
}
