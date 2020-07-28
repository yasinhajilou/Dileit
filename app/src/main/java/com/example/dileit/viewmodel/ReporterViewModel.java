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
    private LiveData<List<WordReviewHistory>> liveReportsReviewed = Transformations.switchMap(liveTimeRange, input -> LiveDataReactiveStreams.fromPublisher(mInternalRepository.getWordReviewedHistoryByRange(input[0], input[1])));
    private LiveData<List<LeitnerReport>> liveReportAdded = Transformations.switchMap(liveTimeRange, input -> LiveDataReactiveStreams.fromPublisher(mInternalRepository.getAddedCardByRange(input[0], input[1])));

    private MutableLiveData<Integer> mSelectedTime = new MutableLiveData<>();
    private MediatorLiveData<Integer> liveSyncedTimeLists = new MediatorLiveData<>();
    private List<LeitnerReport> listAddeds;
    private List<WordReviewHistory> listRevieweds;
    private int timeFilter = -1;

    private MutableLiveData<Integer> mLiveAddedCardCount = new MutableLiveData<>();
    private MutableLiveData<Integer> mLiveReviewedCardCount = new MutableLiveData<>();

    public ReporterViewModel(@NonNull Application application) {
        super(application);
        mInternalRepository = new InternalRepository(application);

        liveSyncedTimeLists.addSource(mSelectedTime, integer -> {
            timeFilter = integer;
            syncAddedReports(timeFilter, listAddeds, listRevieweds);
        });

        liveSyncedTimeLists.addSource(liveReportAdded, leitnerReports -> {
            listAddeds = leitnerReports;
            syncAddedReports(timeFilter, listAddeds, listRevieweds);
        });

        liveSyncedTimeLists.addSource(liveReportsReviewed, leitnerReports -> {
            listRevieweds = leitnerReports;
            syncAddedReports(timeFilter, listAddeds, listRevieweds);
        });
    }

    private void syncAddedReports(int timeFlag, List<LeitnerReport> reportAdded, List<WordReviewHistory> reportReviewed) {
        if (timeFlag != -1 && reportAdded != null && reportReviewed != null) {
            timeFilter = -1;
            listAddeds = null;
            listRevieweds = null;
            liveSyncedTimeLists.setValue(timeFlag);
        }
    }

    public void setSelectedTime(int flag) {
        mSelectedTime.setValue(flag);
    }

    public void setLiveTimeRange(long[] ranges) {
        liveTimeRange.setValue(ranges);
    }

    public LiveData<Integer> getLiveSyncedTimeLists() {
        return liveSyncedTimeLists;
    }

    public LiveData<List<WordReviewHistory>> getLiveReportsReviewed() {
        return liveReportsReviewed;
    }

    public LiveData<List<LeitnerReport>> getLiveReportAdded() {
        return liveReportAdded;
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
