package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dileit.model.Word;
import com.example.dileit.model.repository.DictionaryRepository;

import java.util.List;

public class DictionaryViewModel extends AndroidViewModel implements DictionaryInterface {
    private DictionaryRepository mRepository;
    private MutableLiveData<List<Word>> mDataEng;

    public DictionaryViewModel(@NonNull Application application) {
        super(application);
        mDataEng = new MutableLiveData<>();
        mRepository = new DictionaryRepository(application,this);
    }

    public void getAllEngWords(){
        mRepository.getAllWords();
    }

    public void getEngToPer(String word){
        mRepository.getSpecificWord(word);
    }

    public LiveData<List<Word>> getData(){
        return mDataEng;
    }


    @Override
    public void allEngWords(List<Word> wordList) {
        mDataEng.setValue(wordList);
    }

    @Override
    public void allEngToPer(List<Word> wordList) {
        mDataEng.setValue(wordList);
    }

}
