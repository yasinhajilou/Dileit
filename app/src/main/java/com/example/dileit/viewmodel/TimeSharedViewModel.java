package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TimeSharedViewModel extends ViewModel {
    private MutableLiveData<int[]> mTime = new MutableLiveData<>();

    public void setTime(int[] time) {
        mTime.setValue(time);
    }

    public LiveData<int[]> getTime() {
        return mTime;
    }
}
