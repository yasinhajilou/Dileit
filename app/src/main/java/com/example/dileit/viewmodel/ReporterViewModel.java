package com.example.dileit.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.dileit.model.LeitnerReport;
import com.example.dileit.model.entity.WordReviewHistory;
import com.example.dileit.model.repository.InternalRepository;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;

public class ReporterViewModel extends AndroidViewModel {
    private InternalRepository mInternalRepository;

    private String TAG = ReporterViewModel.class.getSimpleName();

    private MutableLiveData<long[]> liveTimeRange = new MutableLiveData<>();

    private MutableLiveData<Integer> mSelectedTime = new MutableLiveData<>();

    private MutableLiveData<Integer> mLiveAddedCardCount = new MutableLiveData<>();
    private MutableLiveData<Integer> mLiveReviewedCardCount = new MutableLiveData<>();

    public ReporterViewModel(@NonNull Application application) {
        super(application);
        mInternalRepository = new InternalRepository(application);

    }

    public void setSelectedTime(int flag) {
        mSelectedTime.setValue(flag);
    }

    public void setLiveTimeRange(long[] ranges) {
        liveTimeRange.setValue(ranges);
    }


    public void setAddedCardsTimeRange(long start , long end){
        mInternalRepository.getAddedCardsCountByTimeRange(start , end).subscribe(new MaybeObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer integer) {
                mLiveAddedCardCount.postValue(integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void setReviewedCardsTimeRange(long start , long end){
        mInternalRepository.getReviewedCardsCountByTimeRange(start , end).subscribe(new MaybeObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onSuccess(Integer integer) {
                mLiveReviewedCardCount.postValue(integer);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }


    public LiveData<Integer> getTimeFilterFlag() {
        return mSelectedTime;
    }

    public LiveData<Integer> getAllLeitnerCardCount() {
        return LiveDataReactiveStreams.fromPublisher(mInternalRepository.getAllCardCount());
    }

    public LiveData<Integer> getLearnedCardsCount() {
        return LiveDataReactiveStreams.fromPublisher(mInternalRepository.getLearnedCardsCount());
    }

    public LiveData<Integer> getWordReviewedHistoryCount() {
        return LiveDataReactiveStreams.fromPublisher(mInternalRepository.getWordReviewedHistoryCount());
    }

    public LiveData<Integer> getLiveAddedCardCount() {
        return mLiveAddedCardCount;
    }

    public LiveData<Integer> getLiveReviewedCardCount() {
        return mLiveReviewedCardCount;
    }

    public LiveData<long[]> getSelectedTimeRange(){
        return liveTimeRange;
    }
}
