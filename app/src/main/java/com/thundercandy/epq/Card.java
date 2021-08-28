package com.thundercandy.epq;

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

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", term='" + term + '\'' +
                ", definition='" + definition + '\'' +
                '}';
    }
}
