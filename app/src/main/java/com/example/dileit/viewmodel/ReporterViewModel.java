package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.dileit.model.LeitnerReport;
import com.example.dileit.model.repository.InternalRepository;

import java.util.List;

public class ReporterViewModel extends AndroidViewModel {
    private InternalRepository mInternalRepository;
    private MutableLiveData<long[]> liveTimeRange = new MutableLiveData<>();
    private LiveData<List<LeitnerReport>> liveReportsReviewed = Transformations.switchMap(liveTimeRange, input -> LiveDataReactiveStreams.fromPublisher(mInternalRepository.getReviewedCardByRange(input[0], input[1])));
    private LiveData<List<LeitnerReport>> liveReportAdded = Transformations.switchMap(liveTimeRange, input -> LiveDataReactiveStreams.fromPublisher(mInternalRepository.getAddedCardByRange(input[0], input[1])));

    private MutableLiveData<Integer> mTimeFilter = new MutableLiveData<>();
    private MediatorLiveData<Integer> liveTimeFlag = new MediatorLiveData<>();
    private List<LeitnerReport> listAddeds;
    private List<LeitnerReport> listRevieweds;
    private int timeFilter = -1;

    public ReporterViewModel(@NonNull Application application) {
        super(application);
        mInternalRepository = new InternalRepository(application);

        liveTimeFlag.addSource(mTimeFilter, integer -> {
            timeFilter = integer;
            syncAddedReports(timeFilter, listAddeds, listRevieweds);
        });

        liveTimeFlag.addSource(liveReportAdded, leitnerReports -> {
            listAddeds = leitnerReports;
            syncAddedReports(timeFilter, listAddeds, listRevieweds);
        });

        liveTimeFlag.addSource(liveReportsReviewed, leitnerReports -> {
            listRevieweds = leitnerReports;
            syncAddedReports(timeFilter, listAddeds, listRevieweds);
        });
    }

    private void syncAddedReports(int timeFlag, List<LeitnerReport> reportAdded, List<LeitnerReport> reportReviewed) {
        if (timeFlag != -1 && reportAdded != null && reportReviewed != null) {
            liveTimeFlag.setValue(timeFlag);
            timeFilter = -1;
            listAddeds = null;
            listRevieweds = null;
        }
    }

    public void setTimeFilter(int flag) {
        mTimeFilter.setValue(flag);
    }

    public void setLiveTimeRange(long[] ranges) {
        liveTimeRange.setValue(ranges);
    }

    public LiveData<Integer> getTimeFlag() {
        return liveTimeFlag;
    }

    public LiveData<List<LeitnerReport>> getLiveReportsReviewed() {
        return liveReportsReviewed;
    }

    public LiveData<List<LeitnerReport>> getLiveReportAdded() {
        return liveReportAdded;
    }

    public LiveData<Integer> getTimeFilterFlag() {
        return mTimeFilter;
    }

    public LiveData<Integer> getAllLeitnerCardCount() {
        return LiveDataReactiveStreams.fromPublisher(mInternalRepository.getAllCardCount());
    }

    public LiveData<Integer> getLearnedCardsCount() {
        return LiveDataReactiveStreams.fromPublisher(mInternalRepository.getLearnedCardsCount());
    }
}
