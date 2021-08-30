package com.thundercandy.epq;

public class SelectableCategory {

    private int id;
    private String categoryName;
    private int cardCount;
    private boolean selected;

    public SelectableCategory(int id, String categoryName, int cardCount, boolean selected) {
        this.id = id;
        this.categoryName = categoryName;
        this.cardCount = cardCount;
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
