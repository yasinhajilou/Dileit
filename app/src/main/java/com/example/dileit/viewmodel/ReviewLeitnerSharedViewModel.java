package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ReviewLeitnerSharedViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> mReviewedCards = new MutableLiveData<>();
    private MutableLiveData<Integer> mReviewAgainCards = new MutableLiveData<>();

    public ReviewLeitnerSharedViewModel(@NonNull Application application) {
        super(application);
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
}
