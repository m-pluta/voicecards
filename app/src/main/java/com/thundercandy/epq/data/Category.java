package com.thundercandy.epq.data;

import java.util.ArrayList;

public class Category extends DataItem{

    private ArrayList<Card> cards;
    private boolean isExpanded = false;

    public Category(int id, String name, ArrayList<Card> cards, boolean isExpanded) {
        super(id, name);
        this.cards = cards;
        this.isExpanded = isExpanded;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public void flipExpanded() {
        setExpanded(!isExpanded());
    }

//    public void addCard(com.thundercandy.epq.Card card) {
//        if (this.cards == null) {
//            this.cards = new ArrayList<>();
//        }
//        this.cards.add(card);
//    }
//
//    public void updateCategory(Context context) {
//        this.setCards(DbUtils.fetchCategoryCards(context, this.getId()));
//    }

}
