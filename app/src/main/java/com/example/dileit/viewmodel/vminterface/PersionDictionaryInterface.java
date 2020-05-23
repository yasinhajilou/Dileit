package com.example.dileit.viewmodel.vminterface;

import com.example.dileit.model.SearchDictionary;
import com.example.dileit.model.Word;

import java.util.List;

public interface PersionDictionaryInterface {
    void allWords(List<Word> wordList);
    void getSpecificWord(List<SearchDictionary> searchDictionaries);
    void getExactWordData(String data);
}
