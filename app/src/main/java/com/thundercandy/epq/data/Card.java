package com.thundercandy.epq.data;

public class Card {

    private int id;
    private String term;
    private String definition;

    public Card(int id, String term, String definition) {
        this.id = id;
        this.term = term;
        this.definition = definition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
