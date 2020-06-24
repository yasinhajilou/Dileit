package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;

import com.example.dileit.model.SearchDictionary;
import com.example.dileit.model.Word;
import com.example.dileit.model.repository.PersianDictionaryRepository;

import java.util.List;

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
