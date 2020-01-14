package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dileit.model.Word;
import com.example.dileit.model.repository.PersianDictionaryRepository;

import java.util.List;

public class PersianDictionaryViewModel extends AndroidViewModel implements DictionaryInterface {
    private PersianDictionaryRepository mRepository;
    private MutableLiveData<List<Word>> mDataEng;

    public PersianDictionaryViewModel(@NonNull Application application) {
        super(application);
        mDataEng = new MutableLiveData<>();
        mRepository = new PersianDictionaryRepository(application,this);
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
