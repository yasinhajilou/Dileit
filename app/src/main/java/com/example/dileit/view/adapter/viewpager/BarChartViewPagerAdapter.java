package com.example.dileit.view.adapter.viewpager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.PluralsRes;
import androidx.viewpager.widget.PagerAdapter;

import com.example.dileit.R;
import com.example.dileit.model.BarChartInfo;
import com.example.dileit.model.LeitnerReport;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;

import java.util.List;

public class BarChartViewPagerAdapter extends PagerAdapter {
    private List<BarChartInfo> mLeitnerReports;

    public void addData(List<BarChartInfo> leitnerReports) {
        mLeitnerReports = leitnerReports;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mLeitnerReports != null ? mLeitnerReports.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_bar_chart, container, false);
        BarChart barChart = view.findViewById(R.id.bar_chart);
        BarData barData = new BarData();
        return view;
    }
}
