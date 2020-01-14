package com.example.dileit.viewmodel;

import com.example.dileit.model.Word;
import com.example.dileit.model.WordEnglishDic;

import java.util.List;

public interface DictionaryInterface {
    void allEngWords(List<Word> wordList);
    void allEngToPer(List<Word> wordList);
}
