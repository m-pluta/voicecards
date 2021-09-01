package com.thundercandy.epq.data;

import java.util.ArrayList;

public class ExpandableCategory extends Category {

    private boolean isExpanded = false;

    public ExpandableCategory(int id, String name, ArrayList<Card> cards, boolean isExpanded) {
        super(id, name, cards);
        this.isExpanded = isExpanded;
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

}
