package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.dileit.model.WordSearch;
import com.example.dileit.model.repository.AdvancedDictionaryRepository;

import java.util.List;

public class AdvancedDictionaryViewModel extends AndroidViewModel implements AdvancedDictionaryInterface {
    private AdvancedDictionaryRepository mRepository;
    private MutableLiveData<List<WordSearch>> mListSearchWords;


    public AdvancedDictionaryViewModel(@NonNull Application application) {
        super(application);
        mRepository = new AdvancedDictionaryRepository(this);
        mListSearchWords = new MutableLiveData<>();
    }

    public void getListOfWords(String token , String query , String filter , String type){
        mRepository.searchForWord(token , query , filter , type);
    }


    public MutableLiveData<List<WordSearch>> getLiveDataListOfWord(){
        return mListSearchWords;
    }


    @Override
    public void onSuccessfully(List<WordSearch> wordSearches) {
        mListSearchWords.setValue(wordSearches);
    }

    @Override
    public void onError(String error) {

    }
}
