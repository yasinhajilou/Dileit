package com.yasinhajilou.dileit.model;

import java.util.List;

public class BarChartInfo {
    private List<LeitnerReport> mLeitnerReports;

    public BarChartInfo(List<LeitnerReport> leitnerReports) {
        mLeitnerReports = leitnerReports;
    }

    public List<LeitnerReport> getLeitnerReports() {
        return mLeitnerReports;
    }
}
