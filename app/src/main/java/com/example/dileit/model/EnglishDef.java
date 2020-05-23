package com.example.dileit.model;

public class EnglishDef {
    private String cat;
    private String definition;
    private String synonyms;
    private String examples;

    public EnglishDef( String cat, String definition, String synonyms, String examples) {
        this.cat = cat;
        this.definition = definition;
        this.synonyms = synonyms;
        this.examples = examples;
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
}
