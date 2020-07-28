package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.dileit.model.LeitnerReport;
import com.example.dileit.model.entity.WordReviewHistory;
import com.example.dileit.model.repository.InternalRepository;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.FlowableSubscriber;

public class ChartsReporterViewModel extends AndroidViewModel {
    private InternalRepository mInternalRepository;

    private MutableLiveData<List<WordReviewHistory>> mLiveListReviewed = new MutableLiveData<>();

    private MutableLiveData<List<LeitnerReport>> mLiveListAdded = new MutableLiveData<>();

    public ChartsReporterViewModel(@NonNull Application application) {
        super(application);
        mInternalRepository = new InternalRepository(application);
    }

    public void setTimeForReviewedList(long[] time) {

        mInternalRepository.getWordReviewedHistoryByRange(time[0], time[1]).subscribe(new FlowableSubscriber<List<WordReviewHistory>>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(List<WordReviewHistory> wordReviewHistories) {
                mLiveListReviewed.postValue(wordReviewHistories);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    public void setTimeForAddedList(long[] time) {
        mInternalRepository.getAddedCardByRange(time[0], time[1]).subscribe(new FlowableSubscriber<List<LeitnerReport>>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(List<LeitnerReport> leitnerReports) {
                mLiveListAdded.postValue(leitnerReports);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public LiveData<List<WordReviewHistory>> getLiveListReviewed() {
        return mLiveListReviewed;
    }

    public LiveData<List<LeitnerReport>> getLiveListAdded() {
        return mLiveListAdded;
    }
}
