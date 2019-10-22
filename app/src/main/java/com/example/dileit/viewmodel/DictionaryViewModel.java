package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dileit.model.Dictionary;
import com.example.dileit.model.repository.DictionaryRepository;

import java.util.List;

public class DictionaryViewModel extends AndroidViewModel implements DictionaryInterface {
    private DictionaryRepository mRepository;
    private MutableLiveData<List<Dictionary>> mDataEng;

    public DictionaryViewModel(@NonNull Application application) {
        super(application);
        mDataEng = new MutableLiveData<>();
        mRepository = new DictionaryRepository(application,this);
    }

    public void getAllEngWords(){
        mRepository.getAllEngWords();
    }

    public void getEngToPer(String word){
        mRepository.getEngToPer(word);
    }

    public LiveData<List<Dictionary>> getData(){
        return mDataEng;
    }


    @Override
    public void allEngWords(List<Dictionary> dictionaryList) {
        mDataEng.setValue(dictionaryList);
    }

    @Override
    public void allEngToPer(List<Dictionary> dictionaryList) {
        mDataEng.setValue(dictionaryList);
    }

}
