package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dileit.model.WordSearch;
import com.example.dileit.model.repository.AdvancedDictionaryRepository;

import java.util.ArrayList;
import java.util.List;

public class AdvancedDictionaryViewModel extends AndroidViewModel implements AdvancedDictionaryInterface {
    private AdvancedDictionaryRepository mRepository;
    private MutableLiveData<Boolean> shouldGoNextPage;
    private MutableLiveData<List<WordSearch>> mListSearchWords;


    public AdvancedDictionaryViewModel(@NonNull Application application) {
        super(application);
        mRepository = new AdvancedDictionaryRepository(this);
        mListSearchWords = new MutableLiveData<>();
        shouldGoNextPage = new MutableLiveData<>();
    }

    public void setShouldGoNextPage(boolean b){
        shouldGoNextPage.setValue(b);
    }

    public LiveData<Boolean> getBool(){
        return shouldGoNextPage;
    }

    public void getListOfWords(String token , String query , String filter , String type){
        mRepository.searchForWord(token , query , filter , type);
    }


    public LiveData<List<WordSearch>> getLiveDataListOfWord(){
        return mListSearchWords;
    }

    public void resetListData(){
        mListSearchWords.setValue(new ArrayList<>());
    }

    @Override
    public void onSuccessfully(List<WordSearch> wordSearches) {
        mListSearchWords.postValue(wordSearches);
    }

    @Override
    public void onError(String error) {

    }
}
