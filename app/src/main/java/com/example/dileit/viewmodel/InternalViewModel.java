package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dileit.model.entity.Leitner;
import com.example.dileit.model.entity.WordHistory;
import com.example.dileit.model.repository.InternalRepository;
import com.example.dileit.viewmodel.vminterface.InternalInterface;

import java.util.LinkedList;
import java.util.List;

public class InternalViewModel extends AndroidViewModel implements InternalInterface {
    private InternalRepository mRepository;
    private LiveData<List<WordHistory>> mAllWordHistory;
    private MutableLiveData<Long> mLeitnerItemId;

    public InternalViewModel(@NonNull Application application) {
        super(application);
        mRepository = new InternalRepository(application);
        mAllWordHistory = mRepository.getAllWordHistory();
        mLeitnerItemId = new MutableLiveData<>();
    }


    //insert data
    public void insertLeitnerItem(Leitner leitner) {
        mRepository.insertLeitnerItem(leitner, this::onLeitnerAdded);
    }

    public void insertWordHistory(int leitnerId, long time, String word, String wordDef) {
        mRepository.insertWordHistory(leitnerId, time, word, wordDef);
    }

    //get data
    public LiveData<List<WordHistory>> getAllWordHistory() {
        return mAllWordHistory;
    }

    public LiveData<WordHistory> getWordHistoryInfo(String word) {
        return mRepository.getWordHistoryInfo(word);
    }

    public LiveData<Long> getLeitnerAddedId() {
        return mLeitnerItemId;
    }

    public LiveData<Leitner> getLeitnerInfoByWord(String word) {
        return mRepository.getLeitnerInfoByWord(word);
    }

    public LiveData<LinkedList<Leitner>> getAllLeitnerItems(){
       return mRepository.getAllLeitnerItems();
    }

    //update data
    public void updateLeitnerItem(Leitner leitner) {
        mRepository.updateLeitnerItem(leitner);
    }

    public void updateWordHistory(WordHistory wordHistory) {
        mRepository.updateWordHistory(wordHistory);
    }

    //Delete data
    public void deleteLeitnerItem(Leitner leitner) {
        mRepository.deleteLeitnerItem(leitner);
    }

    @Override
    public void onLeitnerAdded(long id) {
        mLeitnerItemId.setValue(id);
    }


}
