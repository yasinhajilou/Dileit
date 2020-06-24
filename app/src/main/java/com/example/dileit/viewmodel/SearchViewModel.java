package com.example.dileit.viewmodel;

import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {
//    private EnglishDictionaryRepository mEnglishDictionaryRepository;
//    private PersianDictionaryRepository mPersianDictionaryRepository;
//
//    private MediatorLiveData<List<SearchDictionary>> mMediatorLiveData;
//    private MutableLiveData<List<SearchDictionary>> mEngData;
//    private MutableLiveData<List<SearchDictionary>> mPerData;
//
//
//    public SearchViewModel(@NonNull Application application) {
//        super(application);
//        mEnglishDictionaryRepository = new EnglishDictionaryRepository(application);
//        mPersianDictionaryRepository = new PersianDictionaryRepository(application);
//        mMediatorLiveData = new MediatorLiveData<>();
//
//        mEngData = new MutableLiveData<>();
//        mPerData = new MutableLiveData<>();
//
//        mMediatorLiveData.addSource(mEngData, searchDictionaries -> {
//            syncData(getEngData(), getPerData());
//        });
//        mMediatorLiveData.addSource(mPerData, searchDictionaries -> {
//            syncData(getEngData(), getPerData());
//        });
//    }
//
//    public void doEngSearch(String text) {
//        mEngData = LiveDataReactiveStreams.fromPublisher(mEnglishDictionaryRepository.doSearch(text));
//    }
//
//    public void doPerSearch(String text) {
//        mPersianDictionaryRepository.searchWords(text);
//    }
//
//    public LiveData<List<SearchDictionary>> getSyncedSearch() {
//        return mMediatorLiveData;
//    }
//
//    private void syncData(LiveData<List<SearchDictionary>> engData, LiveData<List<SearchDictionary>> perData) {
//        if (engData.getValue() != null && perData.getValue() != null) {
//            List<SearchDictionary> allData = new ArrayList<>();
//            allData.addAll(engData.getValue());
//            allData.addAll(perData.getValue());
//            mMediatorLiveData.setValue(allData);
//            mEngData.setValue(null);
//            mPerData.setValue(null);
//        }
//    }
//
//    public void reset() {
//        mMediatorLiveData.setValue(null);
//        mEngData.setValue(null);
//        mPerData.setValue(null);
//    }

}
