package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.dileit.model.SearchDictionary;
import com.example.dileit.model.EnglishDef;
import com.example.dileit.model.repository.EnglishDictionaryRepository;
import com.example.dileit.viewmodel.vminterface.EnglishDictionaryInterface;

import java.util.List;

public class EnglishDictionaryViewModel extends AndroidViewModel implements EnglishDictionaryInterface {
    private EnglishDictionaryRepository mRepository;
    private MutableLiveData<List<EnglishDef>> mListMutableLiveData;
    private LiveData<List<EnglishDef>> mFragmentDataDef;

    public EnglishDictionaryViewModel(@NonNull Application application) {
        super(application);
        mRepository = new EnglishDictionaryRepository(application  , this);
        mListMutableLiveData = new MutableLiveData<>();
        mFragmentDataDef = Transformations.map(mListMutableLiveData , input -> input);
    }

    public void searchEngWordById(int id){
        mRepository.getRefById(id);
    }

    public LiveData<List<EnglishDef>> getAllDefs(){
        return mListMutableLiveData;
    }

    public LiveData<List<EnglishDef>> getFragmentDataDef(){
        return mFragmentDataDef;
    }

    @Override
    public void getEngWord(List<SearchDictionary> searchDictionaries) {

    }

    @Override
    public void getDefById(List<EnglishDef> englishDefs) {
        mListMutableLiveData.postValue(englishDefs);
    }

}
