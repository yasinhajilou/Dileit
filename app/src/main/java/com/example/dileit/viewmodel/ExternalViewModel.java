package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.example.dileit.model.EnglishDef;
import com.example.dileit.model.repository.ExternalDictionaryRepository;

import java.util.List;

public class ExternalViewModel extends AndroidViewModel {
    private ExternalDictionaryRepository mRepository;

    public ExternalViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ExternalDictionaryRepository(application);
    }

    public LiveData<List<EnglishDef>> searchEngWordById(int id) {
        return LiveDataReactiveStreams.fromPublisher(mRepository.getRefById(id));
    }

    public LiveData<String> searchForExactWord(String word) {
        return LiveDataReactiveStreams.fromPublisher(mRepository.getExactWord(word));
    }
}
