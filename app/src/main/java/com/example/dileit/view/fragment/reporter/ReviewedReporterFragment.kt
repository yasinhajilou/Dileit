package com.example.dileit.view.fragment.reporter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.dileit.R
import com.example.dileit.constant.TimeReporterFilter
import com.example.dileit.databinding.FragmentReviewedReporterBinding
import com.example.dileit.model.entity.WordReviewHistory
import com.example.dileit.viewmodel.ChartsReporterViewModel
import com.example.dileit.viewmodel.ReporterViewModel
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import saman.zamani.persiandate.PersianDate
import java.util.*

class ReviewedReporterFragment : Fragment() {
    private var mBinding: FragmentReviewedReporterBinding? = null
    private var mHistoryList: List<WordReviewHistory>? = null
    private val TAG = ReviewedReporterFragment::class.java.simpleName
    private val mMapWeekReviewCounter: MutableMap<Int, Int> = LinkedHashMap()
    private val mMapMonthReviewCounter: MutableMap<String, Int> = LinkedHashMap()
    private val mMapYearReviewCounter: MutableMap<String, Int> = LinkedHashMap()
    private var selectedTime = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentReviewedReporterBinding.inflate(inflater, container, false)
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val reporterViewModel = ViewModelProviders.of(requireActivity()).get(ReporterViewModel::class.java)
        val chartsReporterViewModel = ViewModelProviders.of(this).get(ChartsReporterViewModel::class.java)
        mBinding!!.barChartReviewed.setScaleEnabled(false)
        mBinding!!.barChartReviewed.setFitBars(true) // make the x-axis fit exactly all bars
        reporterViewModel.selectedTimeRange.observe(viewLifecycleOwner, Observer { times: LongArray ->
            //divide on complete day (24 hour)
            val longTime = (times[1] - times[0]) / (24 * 60 * 60 * 1000)
            val time = longTime.toInt()
            when (time) {
                1 -> selectedTime = TimeReporterFilter.DAY
                7 -> selectedTime = TimeReporterFilter.WEEK
                30 -> selectedTime = TimeReporterFilter.MONTH
                365 -> selectedTime = TimeReporterFilter.YEAR
            }
            chartsReporterViewModel.setTimeForReviewedList(times)
        })
        chartsReporterViewModel.liveListReviewed.observe(viewLifecycleOwner, Observer { leitnerReports: List<WordReviewHistory>? ->
            mHistoryList = leitnerReports
            lifecycleScope.launch(Dispatchers.Default) {
                when (selectedTime) {
                    TimeReporterFilter.DAY -> setUpChartDay()
                    TimeReporterFilter.WEEK -> setUpChartWeek()
                    TimeReporterFilter.MONTH -> setUpChartMonth()
                    TimeReporterFilter.YEAR -> setUpChartYear()
                }

            }
        })
    }

    private fun setUpChartDay() {
        //get now hour for getting last 24h
        val mMapDayReviewCounter: MutableMap<String, Int> = LinkedHashMap()
        var startedHour = PersianDate(System.currentTimeMillis()).hour
        val xAxisLabel = ArrayList<String>()

        //create last 24 h
        for (i in 0..23) {
            val reversedHour = String.format("%02d", startedHour--)
            if (startedHour == 0) startedHour = 24
            mMapDayReviewCounter[reversedHour] = 0
            xAxisLabel.add(reversedHour)
        }
        for (wordReviewHistory in mHistoryList!!) {
            val persianDate = PersianDate(wordReviewHistory.reviewedTime)
            val hour = String.format("%02d", persianDate.hour)
            var lastCount = mMapDayReviewCounter[hour]!!
            mMapDayReviewCounter[hour] = ++lastCount
        }
        var c = 0f
        val barEntries: MutableList<BarEntry> = ArrayList()
        for ((_, value) in mMapDayReviewCounter) {
            barEntries.add(BarEntry(c, value.toFloat()))
            c++
        }

        val xAxis = mBinding!!.barChartReviewed.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        mBinding!!.barChartReviewed.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabel)
        xAxis.labelCount = 16

        val set = BarDataSet(barEntries, " ")
        set.setDrawValues(false)
        set.setColors(*ColorTemplate.VORDIPLOM_COLORS)

        val data = BarData(set)
        mBinding!!.barChartReviewed.data = data
//        mBinding!!.barChartReviewed.animateXY(1000, 1000)

        val description = Description()
        description.text = getString(R.string.last_24_h)
        mBinding!!.barChartReviewed.description = description
        mBinding!!.barChartReviewed.invalidate()
    }

    private  fun setUpChartWeek() {
        val xAxisLabelWeek = ArrayList<String>()
        var todayIndexOfWeek = PersianDate(System.currentTimeMillis()).dayOfWeek()
        for (i in 0..6) {
            var day: String
            when (todayIndexOfWeek) {
                0 -> {
                    day = "Sat"
                    xAxisLabelWeek.add(day)
                }
                1 -> {
                    day = "Sun"
                    xAxisLabelWeek.add(day)
                }
                2 -> {
                    day = "Mon"
                    xAxisLabelWeek.add(day)
                }
                3 -> {
                    day = "Tue"
                    xAxisLabelWeek.add(day)
                }
                4 -> {
                    day = "Wed"
                    xAxisLabelWeek.add(day)
                }
                5 -> {
                    day = "Thu"
                    xAxisLabelWeek.add(day)
                }
                6 -> {
                    day = "Fri"
                    xAxisLabelWeek.add(day)
                }
            }
            mMapWeekReviewCounter[todayIndexOfWeek] = 0
            todayIndexOfWeek--
            if (todayIndexOfWeek < 0) todayIndexOfWeek = 6
        }
        for (wordReviewHistory in mHistoryList!!) {
            val persianDate = PersianDate(wordReviewHistory.reviewedTime)
            val weekIndex = persianDate.dayOfWeek()
            var lastCount = mMapWeekReviewCounter[weekIndex]!!
            mMapWeekReviewCounter[weekIndex] = ++lastCount
        }
        val barEntries: MutableList<BarEntry> = ArrayList()
        var m = 0f
        for ((_, value) in mMapWeekReviewCounter) {
            barEntries.add(BarEntry(m, value.toFloat()))
            m++
        }
        val xAxis = mBinding!!.barChartReviewed.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        mBinding!!.barChartReviewed.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabelWeek)
        xAxis.labelCount = 7
        val setWeek = BarDataSet(barEntries, " ")
        setWeek.setDrawValues(false)
        setWeek.setColors(*ColorTemplate.VORDIPLOM_COLORS)
        val dataWeek = BarData(setWeek)
        mBinding!!.barChartReviewed.data = dataWeek
        mBinding!!.barChartReviewed.animateXY(1000, 1000)
        val descriptionWeek = Description()
        descriptionWeek.text = getString(R.string.last_week)
        mBinding!!.barChartReviewed.description = descriptionWeek
        mBinding!!.barChartReviewed.setFitBars(true)
        mBinding!!.barChartReviewed.invalidate()
    }

    private  fun setUpChartMonth() {
        val xAxisLabelMonth = ArrayList<String>()
        val todayTimeStamp = System.currentTimeMillis()
        val persianDate = PersianDate(todayTimeStamp)
        var todayIndexOfMonth = persianDate.shDay
        for (i in 0..29) {
            var day: String
            when (todayIndexOfMonth) {
                1 -> {
                    day = getString(R.string.first)
                    xAxisLabelMonth.add(day)
                    mMapMonthReviewCounter["1"] = 0
                }
                2 -> {
                    day = getString(R.string.second)
                    xAxisLabelMonth.add(day)
                    mMapMonthReviewCounter["2"] = 0
                }
                3 -> {
                    day = getString(R.string.third)
                    xAxisLabelMonth.add(day)
                    mMapMonthReviewCounter["3"] = 0
                }
                else -> {
                    day = todayIndexOfMonth.toString() + getString(R.string.th_date)
                    xAxisLabelMonth.add(day)
                    mMapMonthReviewCounter[todayIndexOfMonth.toString() + ""] = 0
                }
            }
            todayIndexOfMonth--
            if (todayIndexOfMonth == 0) {
                val persianDate1 = PersianDate(todayTimeStamp - 30 * 24 * 60 * 60 * 1000L)
                todayIndexOfMonth = persianDate1.monthLength
            }
        }
        for (wordReviewHistory in mHistoryList!!) {
            val persianDate2 = PersianDate(wordReviewHistory.reviewedTime)
            if (mMapMonthReviewCounter[persianDate2.shDay.toString() + ""] != null) {
                var lastCount = mMapMonthReviewCounter[persianDate2.shDay.toString() + ""]!!
                mMapMonthReviewCounter[persianDate2.shDay.toString() + ""] = ++lastCount
            }
        }
        val barEntries: MutableList<BarEntry> = ArrayList()
        var a = 0f
        for ((_, value) in mMapMonthReviewCounter) {
            barEntries.add(BarEntry(a++, value.toFloat()))
        }
        val xAxis = mBinding!!.barChartReviewed.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelCount = 12
        mBinding!!.barChartReviewed.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabelMonth)
        val setWeek = BarDataSet(barEntries, " ")
        setWeek.setDrawValues(false)
        setWeek.setColors(*ColorTemplate.VORDIPLOM_COLORS)
        val dataWeek = BarData(setWeek)
        mBinding!!.barChartReviewed.data = dataWeek
        mBinding!!.barChartReviewed.animateXY(1000, 1000)
        val descriptionWeek = Description()
        descriptionWeek.text = getString(R.string.last_month)
        mBinding!!.barChartReviewed.description = descriptionWeek
        mBinding!!.barChartReviewed.setFitBars(true)
        mBinding!!.barChartReviewed.invalidate()
    }

    private  fun setUpChartYear() {
        val xAxisLabelYear = ArrayList<String>()
        val todayTimeStamp = System.currentTimeMillis()
        val persianDate = PersianDate(todayTimeStamp)
        var todayIndexOfMonths = persianDate.shMonth
        for (i in 0..11) {
            xAxisLabelYear.add(persianDate.monthName(todayIndexOfMonths))
            mMapYearReviewCounter[todayIndexOfMonths.toString()] = 0
            todayIndexOfMonths--
            if (todayIndexOfMonths == 0) {
                todayIndexOfMonths = 12
            }
        }
        for (wordReviewHistory in mHistoryList!!) {
            val persianDate2 = PersianDate(wordReviewHistory.reviewedTime)
            val date = persianDate2.shMonth
            var lastCount = mMapYearReviewCounter[date.toString() + ""]!!
            mMapYearReviewCounter[date.toString() + ""] = ++lastCount
        }
        val barEntries: MutableList<BarEntry> = ArrayList()
        var a = 0f
        for ((_, value) in mMapYearReviewCounter) {
            barEntries.add(BarEntry(a, value.toFloat()))
            a++
        }
        val xAxis = mBinding!!.barChartReviewed.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        mBinding!!.barChartReviewed.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabelYear)
        xAxis.labelCount = 8
        val setWeek = BarDataSet(barEntries, " ")
        setWeek.setDrawValues(false)
        setWeek.setColors(*ColorTemplate.VORDIPLOM_COLORS)
        val dataWeek = BarData(setWeek)
        mBinding!!.barChartReviewed.data = dataWeek
        mBinding!!.barChartReviewed.animateXY(1000, 1000)
        val descriptionWeek = Description()
        descriptionWeek.text = getString(R.string.last_year)
        mBinding!!.barChartReviewed.description = descriptionWeek
        mBinding!!.barChartReviewed.setFitBars(true)
        mBinding!!.barChartReviewed.invalidate()
    }
}