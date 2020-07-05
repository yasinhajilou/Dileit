package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.dileit.model.LeitnerReport;
import com.example.dileit.model.repository.InternalRepository;

import java.util.List;

public class ReporterViewModel extends AndroidViewModel {
    private InternalRepository mInternalRepository;
    private MutableLiveData<long[]> timeRange = new MutableLiveData<>();
    private LiveData<List<LeitnerReport>> mReportsReviewed = Transformations.switchMap(timeRange, input -> LiveDataReactiveStreams.fromPublisher(mInternalRepository.getReviewedCardByRange(input[0], input[1])));
    private LiveData<List<LeitnerReport>> mReportAdded = Transformations.switchMap(timeRange, input -> LiveDataReactiveStreams.fromPublisher(mInternalRepository.getAddedCardByRange(input[0], input[1])));

    public ReporterViewModel(@NonNull Application application) {
        super(application);
        mInternalRepository = new InternalRepository(application);
    }

    public void setTimeRange(long[] ranges) {
        timeRange.setValue(ranges);
    }


    public LiveData<List<LeitnerReport>> getReportsReviewed() {
        return mReportsReviewed;
    }

    public LiveData<List<LeitnerReport>> getReportAdded() {
        return mReportAdded;
    }

    public LiveData<Integer> getAllLeitnerCardCount() {
        return LiveDataReactiveStreams.fromPublisher(mInternalRepository.getAllCardCount());
    }

    public LiveData<Integer> getLearnedCardsCount() {
        return LiveDataReactiveStreams.fromPublisher(mInternalRepository.getLearnedCardsCount());
    }
}
