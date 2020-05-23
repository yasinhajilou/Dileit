package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dileit.model.SearchDictionary;
import com.example.dileit.model.Word;
import com.example.dileit.model.repository.PersianDictionaryRepository;
import com.example.dileit.viewmodel.vminterface.PersionDictionaryInterface;

import java.util.List;

public class PersianPersionDictionaryViewModel extends AndroidViewModel implements PersionDictionaryInterface {
    private PersianDictionaryRepository mRepository;
    private MutableLiveData<String> mData;

    public PersianPersionDictionaryViewModel(@NonNull Application application) {
        super(application);
        mData = new MutableLiveData<>();
        mRepository = new PersianDictionaryRepository(application, this);
    }


    public void searchForExactWord(String word) {
        mRepository.getExactWord(word);
    }


    public LiveData<String> getWordInfo(){
        return mData;
    }
    @Override
    public void allWords(List<Word> wordList) {

    }

    @Override
    public void getSpecificWord(List<SearchDictionary> searchDictionaries) {

    }

    @Override
    public void getExactWordData(String data) {
        mData.postValue(data);
    }


}
