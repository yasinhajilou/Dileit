package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.dileit.model.WordEnglishDic;
import com.example.dileit.model.repository.EnglishDictionaryRepository;
import com.example.dileit.viewmodel.vminterface.EnglishDictionaryInterface;

import java.util.List;

public class EnglishDictionaryViewModel extends AndroidViewModel implements EnglishDictionaryInterface {
    private EnglishDictionaryRepository mRepository;
    private MutableLiveData<List<WordEnglishDic>> mListMutableLiveData;
    public EnglishDictionaryViewModel(@NonNull Application application) {
        super(application);
        mRepository = new EnglishDictionaryRepository(application , this);
        mListMutableLiveData = new MutableLiveData<>();
    }

    public void doSearch(String word){
        mRepository.doSearch(word);
    }

    public MutableLiveData<List<WordEnglishDic>> getLiveList(){
        return mListMutableLiveData;
    }

    @Override
    public void getEngWord(List<WordEnglishDic> wordEnglishDics) {
        mListMutableLiveData.postValue(wordEnglishDics);
    }
}
