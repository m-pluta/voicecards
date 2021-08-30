package com.thundercandy.epq;

import java.util.ArrayList;

public class Category {

    private int id;
    private String name;
    private ArrayList<Card> cards;
    private boolean isExpanded = false;

    public Category(int id, String name, ArrayList<Card> cards) {
        this.id = id;
        this.name = name;
        this.cards = cards;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cards=" + cards +
                ", isExpanded=" + isExpanded +
                '}';
    }

    public void setExpanded(boolean expanded) {
        this.isExpanded = expanded;
    }

    public void flipExpanded() {
        setExpanded(!isExpanded());
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
}
