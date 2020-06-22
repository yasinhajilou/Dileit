package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;

import com.example.dileit.model.entity.Leitner;
import com.example.dileit.model.entity.WordHistory;
import com.example.dileit.model.repository.InternalRepository;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class InternalViewModel extends AndroidViewModel {
    private InternalRepository mRepository;
    private LiveData<List<WordHistory>> mAllWordHistory;


    //observe for inserted item id
    private MutableLiveData<Long> mLeitnerItemId = new MutableLiveData<>();
    //observe for updated item status
    private MutableLiveData<Boolean> mUpdatedItemStatus = new MutableLiveData<>();
    //observe for deleted item status
    private MutableLiveData<Integer> mDeletedItemStatus = new MutableLiveData<>();

    public InternalViewModel(@NonNull Application application) {
        super(application);
        mRepository = new InternalRepository(application);
        mAllWordHistory = mRepository.getAllWordHistory();
    }


    public void insertWordHistory(int leitnerId, long time, String word, int engId) {
        mRepository.insertWordHistory(leitnerId, time, word, engId);
    }

    public void insertLeitnerItem(Leitner leitner) {
        mRepository.insertLeitnerItem(leitner)
                .subscribe(new SingleObserver<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Long aLong) {
                        mLeitnerItemId.postValue(aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mLeitnerItemId.postValue(-1L);
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

    public LiveData<Long> getLeitnerItemId() {
        return mLeitnerItemId;
    }


    public LiveData<List<WordHistory>> getAllWordHistory() {
        return mAllWordHistory;
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

    public LiveData<List<Leitner>> getCardsByState(int state) {
        return LiveDataReactiveStreams.fromPublisher(mRepository.getCardByState(state));
    }

    public LiveData<List<Leitner>> getCardsByRangeState(int start, int end) {
        return LiveDataReactiveStreams.fromPublisher(mRepository.getGetCardsByRangeState(start, end));
    }

    public LiveData<Leitner> getLeitnerCardById(int id) {
        return LiveDataReactiveStreams.fromPublisher(mRepository.getLeitnerCardById(id));
    }

    public LiveData<List<Leitner>> getAllLeitnerItems() {
        return LiveDataReactiveStreams.fromPublisher(mRepository.getAllLeitnerItems());
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


}
