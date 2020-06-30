package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class TimeSharedViewModel extends AndroidViewModel {
    private MutableLiveData<int[]> mTime;

    public TimeSharedViewModel(@NonNull Application application) {
        super(application);
        mTime = new MutableLiveData<>();
    }

    public void setTime(int[] time) {
        mTime.setValue(time);
    }

    public LiveData<int[]> getTime() {
        return mTime;
    }
}
