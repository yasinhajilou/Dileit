package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dileit.model.Idiom;
import com.example.dileit.model.TranslationWord;
import com.example.dileit.model.WordInformation;
import com.example.dileit.model.WordSearch;

import java.util.List;

public class SharedViewModel extends AndroidViewModel {
    private MutableLiveData<String> mActualWord;
    private MutableLiveData<WordInformation[]> mWordInformation;
    private MutableLiveData<List<TranslationWord>> mTranslationWord;
    private MutableLiveData<List<Idiom>> mIdiom;
    private MutableLiveData<List<WordSearch>> mAdvancedResult;
    private MutableLiveData<String> mVoiceWord;
    private MutableLiveData<String[]> mLeitnerItemData;


    public SharedViewModel(@NonNull Application application) {
        super(application);
        mWordInformation = new MutableLiveData<>();
        mTranslationWord = new MutableLiveData<>();
        mActualWord = new MutableLiveData<>();
        mIdiom = new MutableLiveData<>();
        mAdvancedResult = new MutableLiveData<>();
        mVoiceWord = new MutableLiveData<>();
        mLeitnerItemData = new MutableLiveData<>();
    }

    public void setLeitnerItemData(String[] leitnerItemData) {
        mLeitnerItemData.setValue(leitnerItemData);
    }

    public void setVoiceWord(String s){
        mVoiceWord.setValue(s);
    }

    public LiveData<String> getVoiceWord(){
        return mVoiceWord;
    }

    public void resetVoiceWord(){
        mVoiceWord.setValue("");
    }

    public void setAdvancedResult(List<WordSearch> advancedResult){
        mAdvancedResult.setValue(advancedResult);
    }

    public LiveData<List<WordSearch>> getAdvancedRes(){
        return mAdvancedResult;
    }
    public MutableLiveData<List<Idiom>> getIdiom() {
        return mIdiom;
    }

    public void setIdiom(List<Idiom> idiom) {
        mIdiom.setValue(idiom);
    }

    public void setWordInformation(WordInformation[] wordInformation){
        this.mWordInformation.setValue(wordInformation);
    }
    public MutableLiveData<WordInformation[]> getWordInformation() {
        return mWordInformation;
    }

    public MutableLiveData<List<TranslationWord>> getTranslationWord() {
        return mTranslationWord;
    }

    public void setTranslationWord(List<TranslationWord> translationWord) {
        mTranslationWord.setValue(translationWord);
    }

    public MutableLiveData<String> getActualWord() {
        return mActualWord;
    }

    public MutableLiveData<String[]> getLeitnerItemData() {
        return mLeitnerItemData;
    }

    public void setActualWord(String actualWord) {
        mActualWord.setValue(actualWord);
    }
}
