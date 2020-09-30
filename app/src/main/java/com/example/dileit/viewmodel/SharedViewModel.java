package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dileit.model.EnglishDef;
import com.example.dileit.model.Idiom;
import com.example.dileit.model.TranslationWord;
import com.example.dileit.model.WordInformation;

import java.util.List;

public class SharedViewModel extends AndroidViewModel {

    private MutableLiveData<String> mActualWord;
    private MutableLiveData<WordInformation[]> mWordInformation;
    private MutableLiveData<List<WordInformation>> mTranslationWord;
    private MutableLiveData<List<Idiom>> mIdiom;
    private MutableLiveData<String> mVoiceWord;
//    private MutableLiveData<String[]> mLeitnerItemData;
    private MutableLiveData<Boolean> mCostumeCheck;
    private MutableLiveData<Boolean> mSaveBtnCheck;

    private MutableLiveData<List<EnglishDef>> mEngDefList;

    private String translation;
    private String secondTranslation;

    public SharedViewModel(@NonNull Application application) {
        super(application);
        mWordInformation = new MutableLiveData<>();
        mTranslationWord = new MutableLiveData<>();
        mActualWord = new MutableLiveData<>();
        mIdiom = new MutableLiveData<>();
        mVoiceWord = new MutableLiveData<>();
//        mLeitnerItemData = new MutableLiveData<>();
        mCostumeCheck = new MutableLiveData<>();
        mSaveBtnCheck = new MutableLiveData<>();
        mEngDefList = new MutableLiveData<>();
    }


    public void setEngDefList(List<EnglishDef> engDefList){
        mEngDefList.setValue(engDefList);
    }

    public LiveData<List<EnglishDef>> getEngDefList(){
        return mEngDefList;
    }
    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public void setSecondTranslation(String secondTranslation) {
        this.secondTranslation = secondTranslation;
    }

    public String getTranslation() {
        return translation;
    }

    public String getSecondTranslation() {
        return secondTranslation;
    }


    public void setSaveBtnCheck(boolean saveBtnCheck) {
        mSaveBtnCheck.setValue(saveBtnCheck);
    }

    public LiveData<Boolean> getSaveButtonCheck() {
        return mSaveBtnCheck;
    }

    public void setCostumeCheck(boolean costumeCheck) {
        mCostumeCheck.setValue(costumeCheck);
    }

    public LiveData<Boolean> getCostumeCheck() {
        return mCostumeCheck;
    }

//    public void setLeitnerItemData(String[] leitnerItemData) {
//        mLeitnerItemData.setValue(leitnerItemData);
//    }

    public void setVoiceWord(String s) {
        mVoiceWord.setValue(s);
    }

    public LiveData<String> getVoiceWord() {
        return mVoiceWord;
    }

    public void resetVoiceWord() {
        mVoiceWord.setValue("");
    }

    public MutableLiveData<List<Idiom>> getIdiom() {
        return mIdiom;
    }

    public void setIdiom(List<Idiom> idiom) {
        mIdiom.setValue(idiom);
    }


    public LiveData<List<WordInformation>> getTranslationWord() {
        return mTranslationWord;
    }

    public void setTranslationWord(List<WordInformation> translationWord) {
        mTranslationWord.setValue(translationWord);
    }


//    public MutableLiveData<String[]> getLeitnerItemData() {
//        return mLeitnerItemData;
//    }


}
