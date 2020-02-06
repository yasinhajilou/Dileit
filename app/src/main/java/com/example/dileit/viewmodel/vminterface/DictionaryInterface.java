package com.example.dileit.viewmodel.vminterface;

import com.example.dileit.model.Word;

import java.util.List;

public interface DictionaryInterface {
    void allEngWords(List<Word> wordList);
    void allEngToPer(List<Word> wordList);
}
