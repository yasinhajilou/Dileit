package com.example.dileit.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.dileit.model.entity.Leitner;
import com.example.dileit.model.entity.WordHistory;
import com.example.dileit.model.repository.InternalRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class InternalViewModel extends AndroidViewModel {
    private InternalRepository mRepository;
    private String TAG = InternalViewModel.class.getSimpleName();
    //observe for inserted item id
    private MutableLiveData<Long> mAddedLeitnerItemId = new MutableLiveData<>();
    //observe for updated item status
    private MutableLiveData<Boolean> mUpdatedItemStatus = new MutableLiveData<>();
    //observe for deleted item status
    private MutableLiveData<Integer> mDeletedItemStatus = new MutableLiveData<>();
    //observe for existed item result
    private MutableLiveData<Boolean> mExistedItem = new MutableLiveData<>();

    //observing cards by their state
    private MutableLiveData<Integer> mBoxState = new MutableLiveData<>();

    private LiveData<List<Leitner>> mLearnedCardByBox = Transformations.switchMap(mBoxState, new Function<Integer, LiveData<List<Leitner>>>() {
        @Override
        public LiveData<List<Leitner>> apply(Integer input) {
            return LiveDataReactiveStreams.fromPublisher(mRepository.getCardByState(input));
        }
    });


    //observing delete history res
    private MutableLiveData<Boolean> mDeletedHistoryStatus = new MutableLiveData<>();

    public InternalViewModel(@NonNull Application application) {
        super(application);
        mRepository = new InternalRepository(application);
    }

    public void setBoxState(int state) {
        mBoxState.setValue(state);
    }

    //insert data
    public void insertWordHistory(WordHistory wordHistory) {
        mRepository.insertWordHistory(wordHistory).subscribe();
    }

    public void insertLeitnerItem(Leitner leitner) {
        mRepository.insertLeitnerItem(leitner)
                .subscribe(new SingleObserver<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Long aLong) {
                        mAddedLeitnerItemId.postValue(aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAddedLeitnerItemId.postValue(-1L);
                    }
                });
    }


    //update data
    public void updateLeitnerItem(Leitner leitner) {
        mRepository.updateLeitnerItem(leitner)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        mUpdatedItemStatus.postValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mUpdatedItemStatus.postValue(false);
                    }
                });
    }

    public LiveData<Long> getAddedLeitnerItemId() {
        return mAddedLeitnerItemId;
    }


    //getData

    public LiveData<Boolean> getDeletedHistoryResult() {
        return mDeletedHistoryStatus;
    }

    public void getExistedLeitner(String word) {
        mRepository.getExistingLeitner(word)
                .subscribe(new MaybeObserver<Leitner>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(Leitner leitner) {
                        mExistedItem.postValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        mExistedItem.postValue(false);
                    }
                });
    }

    public LiveData<Boolean> getBooleanExistedCard() {
        return mExistedItem;
    }

    public LiveData<List<WordHistory>> getAllWordHistory() {
        return LiveDataReactiveStreams.fromPublisher(mRepository.getAllWordHistory());
    }


    public LiveData<Boolean> getUpdateItemStatus() {
        return mUpdatedItemStatus;
    }

    public LiveData<Integer> getDeletedItemStatus() {
        return mDeletedItemStatus;
    }

    public LiveData<Leitner> getLeitnerInfoByWord(String word) {
        return LiveDataReactiveStreams.fromPublisher(mRepository.getLeitnerInfoByWord(word));
    }

    public LiveData<List<Leitner>> getLearnedCardByBox() {
        return mLearnedCardByBox;
    }

    public LiveData<List<Leitner>> getCardsByState(int state) {
        return LiveDataReactiveStreams.fromPublisher(mRepository.getCardByState(state));
    }

    public LiveData<Leitner> getLeitnerCardById(int id) {
        return LiveDataReactiveStreams.fromPublisher(mRepository.getLeitnerCardById(id));
    }

    public LiveData<List<Leitner>> getAllLeitnerItems() {
        return LiveDataReactiveStreams.fromPublisher(mRepository.getAllLeitnerItems());
    }

    public LiveData<List<Leitner>> getTodayList() {
        return LiveDataReactiveStreams.fromPublisher(mRepository.getTodayList());
    }

    public LiveData<Integer> getTodayListSize() {
        return LiveDataReactiveStreams.fromPublisher(mRepository.getTodayListSize());
    }

    public LiveData<Integer> getNewCardsCount() {
        return LiveDataReactiveStreams.fromPublisher(mRepository.getNewCardsCount());
    }

    public LiveData<Integer> getLearnedCardsCount() {
        return LiveDataReactiveStreams.fromPublisher(mRepository.getLearnedCardsCount());
    }

    public LiveData<Integer> getReviewingCardCount() {
        return LiveDataReactiveStreams.fromPublisher(mRepository.getReviewingCardCount());
    }

    //Delete data
    public void deleteLeitnerItem(Leitner leitner) {
        mRepository.deleteLeitnerItem(leitner)
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        mDeletedItemStatus.postValue(integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mDeletedItemStatus.postValue(-1);
                    }
                });
    }


    public void deleteAllHistory() {
        mRepository.deleteAllHistory().subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                mDeletedHistoryStatus.postValue(true);
            }

            @Override
            public void onError(Throwable e) {
                mDeletedHistoryStatus.postValue(false);
            }
        });
    }


}
