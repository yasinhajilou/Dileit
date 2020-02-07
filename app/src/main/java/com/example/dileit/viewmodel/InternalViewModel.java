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
    private InternalRepository mRepository;
    private LiveData<List<WordHistory>> mAllWordHistory;

    public InternalViewModel(@NonNull Application application) {
        super(application);
        mRepository = new InternalRepository(application);
        mAllWordHistory = mRepository.getAllWordHistory();
    }


    //insert data
    public void insertLeitnerItem(Leitner leitner){
        mRepository.insertLeitnerItem(leitner);
    }

    public void insertWordHistory( int leitnerId, long time, String word, String wordDef){
        mRepository.insertWordHistory(leitnerId,time,word,wordDef);
    }

    //get data
    public LiveData<List<WordHistory>> getAllWordHistory(){
        return mAllWordHistory;
    }

    public LiveData<WordHistory> getWordHistoryInfo(String word){
        return mRepository.getWordHistoryInfo(word);
    }

    //update data
    public void updateLeitnerItem(Leitner leitner){
        mRepository.updateLeitnerItem(leitner);
    }
}
