package com.thundercandy.epq.data;

import java.util.ArrayList;

public class Category extends DataItem {

    private ArrayList<Card> cards;

    public Category(int id, String name, ArrayList<Card> cards) {
        super(id, name);

        if (cards == null) {
            cards = new ArrayList<>();
        }
        this.cards = cards;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

//    public void updateCategory(Context context) {
//        this.setCards(DbUtils.fetchCategoryCards(context, this.getId()));
//    }
}
