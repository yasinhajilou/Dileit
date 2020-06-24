package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.example.dileit.model.EnglishDef;

import java.util.List;

public class EnglishDictionaryViewModel extends AndroidViewModel {
    private EnglishDictionaryRepository mRepository;
    public EnglishDictionaryViewModel(@NonNull Application application) {
        super(application);
        mRepository = new EnglishDictionaryRepository(application);
    }

    public LiveData<List<EnglishDef>> searchEngWordById(int id) {
       return LiveDataReactiveStreams.fromPublisher(mRepository.getRefById(id));
    }

}
