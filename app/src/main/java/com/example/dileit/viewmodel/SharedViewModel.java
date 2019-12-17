package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.dileit.model.Idiom;
import com.example.dileit.model.TranslationWord;
import com.example.dileit.model.WordInformation;

import java.util.List;

public class SharedViewModel extends AndroidViewModel {
    private MutableLiveData<String> mActualWord;
    private MutableLiveData<WordInformation> mWordInformation;
    private MutableLiveData<List<TranslationWord>> mTranslationWord;
    private MutableLiveData<Idiom> mIdiom;


    public SharedViewModel(@NonNull Application application) {
        super(application);
        mWordInformation = new MutableLiveData<>();
        mTranslationWord = new MutableLiveData<>();
        mActualWord = new MutableLiveData<>();
        mIdiom = new MutableLiveData<>();
    }


    public MutableLiveData<Idiom> getIdiom() {
        return mIdiom;
    }

    public void setIdiom(Idiom idiom) {
        mIdiom.setValue(idiom);
    }

    public void setWordInformation(WordInformation wordInformation){
        this.mWordInformation.setValue(wordInformation);
    }
    public MutableLiveData<WordInformation> getWordInformation() {
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

    public void setActualWord(String actualWord) {
        mActualWord.setValue(actualWord);
    }
}
