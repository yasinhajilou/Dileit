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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchDictionary that = (SearchDictionary) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, engId);
    }
}
