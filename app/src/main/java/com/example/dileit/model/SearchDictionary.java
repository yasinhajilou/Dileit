package com.example.dileit.model;

import java.util.Objects;

public class SearchDictionary {
    private String title;
    private int engId;

    public SearchDictionary(String title, int engId) {
        this.title = title;
        this.engId = engId;
    }

    public String getTitle() {
        return title;
    }

    public int getEngId() {
        return engId;
    }

    @Override
    public boolean equals(Object o) {
        SearchDictionary that = (SearchDictionary) o;
        if (that != null) {
            return that.title.equals(this.title);
        } else
            return false;
    }

    @Override
    public int hashCode() {
        return this.title.hashCode();
    }
}
