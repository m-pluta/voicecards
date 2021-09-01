package com.thundercandy.epq.data;

public class SelectableCategory extends DataItem {

    private int cardCount;
    private boolean selected;

    public SelectableCategory(int id, String name, int cardCount, boolean selected) {
        super(id, name);
        this.cardCount = cardCount;
        this.selected = selected;
    }

    public String getCategoryName() {
        return super.getName();
    }

    public void setCategoryName(String name) {
        super.setName(name);
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
