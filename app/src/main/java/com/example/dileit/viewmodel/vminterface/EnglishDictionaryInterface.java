package com.example.dileit.viewmodel.vminterface;

import com.example.dileit.model.EnglishDef;
import com.example.dileit.model.SearchDictionary;

import java.util.List;

public interface EnglishDictionaryInterface {
    void getEngWord(List<SearchDictionary> searchDictionaries);
    void getDefById(List<EnglishDef> searchDictionaries);
}
