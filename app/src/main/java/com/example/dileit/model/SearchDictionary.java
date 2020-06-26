package com.example.dileit.model;


public class SearchDictionary implements Comparable<SearchDictionary> {
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
        if (this.title.equals(that.title)) {
            if (this.engId == 0 && that.engId != 0)
                this.engId = that.engId;

            if (that.engId == 0 && this.engId != 0)
                that.engId = this.engId;

            return true;
        } else
            return false;
    }

    @Override
    public int hashCode() {
        return this.title.hashCode();
    }


    @Override
    public int compareTo(SearchDictionary searchDictionary) {
        return this.title.compareToIgnoreCase(searchDictionary.title);
    }
}
