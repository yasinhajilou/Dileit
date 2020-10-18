package com.yasinhajilou.dileit.model.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.yasinhajilou.dileit.model.entity.Leitner;
import com.yasinhajilou.dileit.model.entity.WordHistory;

public class WordLeitner {
    @Embedded
    public WordHistory  mWordHistory;

    @Relation(parentColumn = "leitnerId" , entityColumn = "id")
    public Leitner mLeitner;

}
