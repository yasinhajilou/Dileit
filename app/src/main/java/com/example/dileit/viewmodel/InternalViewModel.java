package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dileit.model.entity.Leitner;
import com.example.dileit.model.entity.WordHistory;
import com.example.dileit.model.repository.InternalRepository;

import java.util.List;

public class InternalViewModel extends AndroidViewModel {
    InternalRepository mRepository;
    LiveData<List<WordHistory>> mLiveData;

    public InternalViewModel(@NonNull Application application) {
        super(application);
        mRepository = new InternalRepository(application);
        mLiveData = mRepository.getLiveData();
    }

    public void insertLeitnerItem(Leitner leitner){
        mRepository.insertLeitnerItem(leitner);
    }

    public void insertWordHistory( int leitnerId, long time, String word, String wordDef){
        mRepository.insertWordHistory(leitnerId,time,word,wordDef);
    }
    public LiveData<List<WordHistory>> getLiveData(){
        return mLiveData;
    }
}
