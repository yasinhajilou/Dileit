package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.dileit.model.WordDefinition;

public class SharedViewModel extends AndroidViewModel {
    private MutableLiveData<WordDefinition> mData;
    public SharedViewModel(@NonNull Application application) {
        super(application);
        mData = new MutableLiveData<>();
    }

    public void setData(WordDefinition data){
        mData.setValue(data);
    }
    public MutableLiveData<WordDefinition> getData() {
        return mData;
    }
}
