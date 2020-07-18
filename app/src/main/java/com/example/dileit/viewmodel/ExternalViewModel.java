package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.example.dileit.model.EnglishDef;
import com.example.dileit.model.SearchDictionary;
import com.example.dileit.model.repository.ExternalDictionaryRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ExternalViewModel extends AndroidViewModel {
    private ExternalDictionaryRepository mRepository;
    private MediatorLiveData<List<SearchDictionary>> mSyncedSearchList;
    private LiveData<List<SearchDictionary>> mGetPer, mGetEng;

    public ExternalViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ExternalDictionaryRepository(application);

        mSyncedSearchList = new MediatorLiveData<>();

    }

    private void syncSearchedData(LiveData<List<SearchDictionary>> getPer, LiveData<List<SearchDictionary>> getEng) {
        if (getPer.getValue() != null && getEng.getValue() != null) {
            Set<SearchDictionary> set = new TreeSet<>();
            set.addAll(getEng.getValue());
            set.addAll(getPer.getValue());
            List<SearchDictionary> searchDictionaries = new ArrayList<>(set);
            mSyncedSearchList.setValue(searchDictionaries);
        }
    }

    public LiveData<List<SearchDictionary>> getSyncedSearchResult() {
        return mSyncedSearchList;
    }

    public LiveData<List<EnglishDef>> searchEngWordById(int id) {
        return LiveDataReactiveStreams.fromPublisher(mRepository.getRefById(id));
    }

    public LiveData<String> searchForExactWord(String word) {
        return LiveDataReactiveStreams.fromPublisher(mRepository.getExactWord(word));
    }


    public LiveData<List<SearchDictionary>> getSearchEng(String word) {
        mGetEng = LiveDataReactiveStreams.fromPublisher(mRepository.doEngSearch(word));
        mSyncedSearchList.addSource(mGetEng, searchDictionaries -> {
            syncSearchedData(mGetPer, mGetEng);
        });
        return mGetEng;
    }

    public LiveData<List<SearchDictionary>> getSearchPer(String word) {
        mGetPer = LiveDataReactiveStreams.fromPublisher(mRepository.doPersianSearch(word));
        mSyncedSearchList.addSource(mGetPer, searchDictionaries -> syncSearchedData(mGetPer, mGetEng));
        return mGetPer;
    }

    public void removeSearchResults(){
        mSyncedSearchList.setValue(new ArrayList<>());
    }
    public void closeExternalDbs() {
        mRepository.closeExternalDatabases();
    }
}
