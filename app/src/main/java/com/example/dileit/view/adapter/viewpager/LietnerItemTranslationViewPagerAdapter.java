package com.example.dileit.view.adapter.viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class LietnerItemTranslationViewPagerAdapter extends FragmentStateAdapter {

    public LietnerItemTranslationViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
