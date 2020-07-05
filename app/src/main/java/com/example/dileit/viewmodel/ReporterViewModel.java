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

public class ReporterViewModel extends AndroidViewModel {
    private InternalRepository mInternalRepository;
    private MutableLiveData<long[]> timeRange = new MutableLiveData<>();
    private LiveData<LeitnerReport> mReportsReviewed = Transformations.switchMap(timeRange, input -> null);

    public ReporterViewModel(@NonNull Application application) {
        super(application);
        mInternalRepository = new InternalRepository(application);
    }


    public LiveData<Integer> getAllLeitnerCardCount() {
        return LiveDataReactiveStreams.fromPublisher(mInternalRepository.getAllCardCount());
    }

    public LiveData<Integer> getLearnedCardsCount() {
        return LiveDataReactiveStreams.fromPublisher(mInternalRepository.getLearnedCardsCount());
    }
}
