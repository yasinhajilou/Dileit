package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.dileit.model.WordInformation;

public class SharedViewModel extends AndroidViewModel {
    private MutableLiveData<WordInformation> mData;
    public SharedViewModel(@NonNull Application application) {
        super(application);
        mData = new MutableLiveData<>();
    }

    public void setData(WordInformation data){
        mData.setValue(data);
    }
    public MutableLiveData<WordInformation> getData() {
        return mData;
    }
}
