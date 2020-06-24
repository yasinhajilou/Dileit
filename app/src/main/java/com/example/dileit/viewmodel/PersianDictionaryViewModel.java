package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

public class PersianDictionaryViewModel extends AndroidViewModel {
    private PersianDictionaryRepository mRepository;


    public PersianDictionaryViewModel(@NonNull Application application) {
        super(application);
        mRepository = new PersianDictionaryRepository(application);
    }


    public LiveData<String> searchForExactWord(String word) {
        return LiveDataReactiveStreams.fromPublisher(mRepository.getExactWord(word));
    }

}
