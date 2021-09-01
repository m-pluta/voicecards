package com.thundercandy.epq.data;

public class SelectableCategory extends DataItem {

    private int cardCount;
    private boolean selected;

    public SelectableCategory(int id, String name, int cardCount, boolean selected) {
        super(id, name);
        this.cardCount = cardCount;
        this.selected = selected;
    }

    public int getCardCount() {
        return cardCount;
    }

    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
