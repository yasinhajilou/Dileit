package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

public class ReviewLeitnerSharedViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> mReviewedCards = new MutableLiveData<>();
    private MutableLiveData<Integer> mReviewAgainCards = new MutableLiveData<>();

    private MediatorLiveData<int[]> mLiveSyncedCards = new MediatorLiveData<>();
    private int mAllReviewedCards = -1 ,mReviewAgain = -1;
    public ReviewLeitnerSharedViewModel(@NonNull Application application) {
        super(application);
        mLiveSyncedCards.addSource(mReviewedCards , integer -> {
            mAllReviewedCards = integer;
            syncData(mAllReviewedCards , mReviewAgain);
        });

        mLiveSyncedCards.addSource(mReviewAgainCards , integer -> {
            mReviewAgain = integer;
            syncData(mAllReviewedCards , mReviewAgain);
        });
    }

    public void syncData(int allReviewedCards , int reviewAgain){
        if (mAllReviewedCards != -1 && mReviewAgain != -1){
            mLiveSyncedCards.setValue(new int[]{allReviewedCards , reviewAgain});
        }
    }

    public void setReviewedCards(int count) {
        mReviewedCards.setValue(count);
    }

    public void setReviewAgainCards(int count) {
        mReviewAgainCards.setValue(count);
    }

    public LiveData<Integer> getReviewedCards() {
        return mReviewedCards;
    }

    public LiveData<Integer> getReviewAgainCards() {
        return mReviewAgainCards;
    }

    public LiveData<int[]> getSyncedCards(){
        return mLiveSyncedCards;
    }
}
