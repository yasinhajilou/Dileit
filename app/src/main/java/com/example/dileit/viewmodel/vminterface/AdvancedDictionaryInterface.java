package com.example.dileit.viewmodel.vminterface;

import com.example.dileit.model.WordSearch;

import java.util.List;

public interface AdvancedDictionaryInterface {
    void onSuccessfully(List<WordSearch> wordSearches);
    void onError(String error);
}
