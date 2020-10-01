package com.example.dileit.view.adapter.viewpager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.example.dileit.R;

import java.util.ArrayList;
import java.util.List;

public class AddNewLeitnerViewPagerAdapter extends PagerAdapter {
    List<String> titles = new ArrayList<>();
    List<String> mInformation = new ArrayList<>();

    @Override
    public int getCount() {
        return titles.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_pager_leitner_info, container, false);
        view.setTag(titles.get(position));
        TextView textView = view.findViewById(R.id.edt_pager_leitner_info);
        textView.setText(mInformation.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (titles.size() > 0)
            return titles.get(position);
        else return null;
    }

    public void addData(String title, String info) {
        titles.add(title);
        mInformation.add(info);
        notifyDataSetChanged();
    }


}
