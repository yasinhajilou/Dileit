package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dileit.model.EnglishDef;
import com.example.dileit.model.SearchDictionary;
import com.example.dileit.model.Word;
import com.example.dileit.model.repository.EnglishDictionaryRepository;
import com.example.dileit.model.repository.PersianDictionaryRepository;
import com.example.dileit.viewmodel.vminterface.PersionDictionaryInterface;
import com.example.dileit.viewmodel.vminterface.EnglishDictionaryInterface;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends AndroidViewModel implements EnglishDictionaryInterface, PersionDictionaryInterface {
    private EnglishDictionaryRepository mEnglishDictionaryRepository;
    private PersianDictionaryRepository mPersianDictionaryRepository;

    private MediatorLiveData<List<SearchDictionary>> mMediatorLiveData;
    private MutableLiveData<List<SearchDictionary>> mEngData;
    private MutableLiveData<List<SearchDictionary>> mPerData;



    public SearchViewModel(@NonNull Application application) {
        super(application);
        mEnglishDictionaryRepository = new EnglishDictionaryRepository(application, this);
        mPersianDictionaryRepository = new PersianDictionaryRepository(application , this );
        mMediatorLiveData = new MediatorLiveData<>();
        mEngData = new MutableLiveData<>();
        mPerData = new MutableLiveData<>();

        mMediatorLiveData.addSource(getEngData(), searchDictionaries -> {
            syncData(getEngData(), getPerData());
        });
        mMediatorLiveData.addSource(getPerData() , searchDictionaries -> {
            syncData(getEngData() , getPerData());
        });
    }

    public void doEngSearch(String text) {
        mEnglishDictionaryRepository.doSearch(text);
    }

    public void doPerSearch(String text){
        mPersianDictionaryRepository.getSpecificWord(text);
    }

    private LiveData<List<SearchDictionary>> getEngData() {
        return mEngData;
    }

    private LiveData<List<SearchDictionary>> getPerData() {
        return mPerData;
    }

    public LiveData<List<SearchDictionary>> getSyncedSearch(){
        return mMediatorLiveData;
    }
    private void syncData(LiveData<List<SearchDictionary>> engData, LiveData<List<SearchDictionary>> perData) {
        if (engData.getValue() != null && perData.getValue() != null) {
            List<SearchDictionary> allData = new ArrayList<>();
            allData.addAll(engData.getValue());
            allData.addAll(perData.getValue());
            mMediatorLiveData.setValue(allData);
            mEngData.setValue(null);
            mPerData.setValue(null);
        }
    }

    @Override
    public void getEngWord(List<SearchDictionary> searchDictionaries) {
        mEngData.postValue(searchDictionaries);
    }

    @Override
    public void getDefById(List<EnglishDef> searchDictionaries) {

    }


    @Override
    public void allWords(List<Word> wordList) {

    }

    @Override
    public void getSpecificWord(List<SearchDictionary> searchDictionaries) {
        mPerData.postValue(searchDictionaries);
    }

    @Override
    public void getExactWordData(String data) {

    }
}
