package com.example.dileit.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.dileit.model.Dictionary;
import com.example.dileit.model.repository.DictionaryRepository;

import java.util.List;

public class DictionaryViewModel extends AndroidViewModel implements DictionaryInterface {
    private DictionaryRepository mRepository;
    private MutableLiveData<List<Dictionary>> mData;

    public DictionaryViewModel(@NonNull Application application) {
        super(application);
        mData = new MutableLiveData<>();
        mRepository = new DictionaryRepository(application,this);
    }

    public void setData(){
        mRepository.getAllEngWords();
    }

    public MutableLiveData<List<Dictionary>> getData(){
        return mData;
    }
    @Override
    public void allEngWords(List<Dictionary> dictionaryList) {
        mData.setValue(dictionaryList);
    }
}
