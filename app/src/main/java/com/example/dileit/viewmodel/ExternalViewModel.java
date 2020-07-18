package com.example.dileit.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dileit.model.EnglishDef;
import com.example.dileit.model.SearchDictionary;
import com.example.dileit.model.repository.ExternalDictionaryRepository;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Consumer;

public class ExternalViewModel extends AndroidViewModel {
    private String TAG = ExternalViewModel.class.getSimpleName();
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
            Log.d(TAG, "syncSearchedData: " + getEng.getValue().size() + " " + getPer.getValue().size());
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


    public void getSearchEng(String word) {
        if (!word.equals("")) {
            mGetEng = LiveDataReactiveStreams.fromPublisher(mRepository.doEngSearch(word));
            mSyncedSearchList.addSource(mGetEng, searchDictionaries -> {
                syncSearchedData(mGetPer, mGetEng);
            });
        }
    }

    public void getSearchPer(String word) {
        if (!word.equals("")) {
            mGetPer = LiveDataReactiveStreams.fromPublisher(mRepository.doPersianSearch(word));
            mSyncedSearchList.addSource(mGetPer, searchDictionaries -> {
                syncSearchedData(mGetPer, mGetEng);
            });
        }
    }

    public void removeSearchResults() {
        mSyncedSearchList.setValue(null);
    }

    public void closeExternalDbs() {
        mRepository.closeExternalDatabases();
    }
}
