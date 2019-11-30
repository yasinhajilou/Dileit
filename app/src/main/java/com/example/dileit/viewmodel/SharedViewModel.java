package com.example.dileit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.dileit.model.TranslationWord;
import com.example.dileit.model.WordInformation;

import java.util.List;

public class SharedViewModel extends AndroidViewModel {
    private MutableLiveData<WordInformation> mWordInformation;
    private MutableLiveData<List<TranslationWord>> mTranslationWord;
    public SharedViewModel(@NonNull Application application) {
        super(application);
        mWordInformation = new MutableLiveData<>();
        mTranslationWord = new MutableLiveData<>();
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
}
