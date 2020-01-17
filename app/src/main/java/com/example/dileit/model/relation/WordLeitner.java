package com.example.dileit.model.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.dileit.model.entity.Leitner;
import com.example.dileit.model.entity.WordHistory;

public class WordLeitner {
    @Embedded
    public WordHistory  mWordHistory;

    @Relation(parentColumn = "leitnerId" , entityColumn = "id")
    public Leitner mLeitner;

}
