package com.yasinhajilou.dileit.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TimeSharedViewModel extends ViewModel {
    private MutableLiveData<int[]> mTime = new MutableLiveData<>();
    private MutableLiveData<Boolean> mCancelListener = new MutableLiveData<>();

    public void setTime(int[] time) {
        mTime.setValue(time);
    }

    public void setCancelListener(boolean status) {
        mCancelListener.setValue(status);
    }

    public LiveData<Boolean> getCancelListener() {
        return mCancelListener;
    }

    public LiveData<int[]> getTime() {
        return mTime;
    }
}
