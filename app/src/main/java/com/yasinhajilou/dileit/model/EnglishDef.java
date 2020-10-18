package com.yasinhajilou.dileit.model;

public class EnglishDef {
    private String cat;
    private String definition;
    private String synonyms;
    private String examples;
    private String similar;
    private String antonyms;

    public EnglishDef(String cat, String definition, String synonyms, String examples, String similar, String antonyms) {
        this.cat = cat;
        this.definition = definition;
        this.synonyms = synonyms;
        this.examples = examples;
        this.similar = similar;
        this.antonyms = antonyms;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public String getExamples() {
        return examples;
    }

    public String getCat() {
        return cat;
    }

    public String getDefinition() {
        return definition;
    }

    public String getSimilar() {
        return similar;
    }

    public String getAntonyms() {
        return antonyms;
    }
}
