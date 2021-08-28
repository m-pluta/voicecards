package com.thundercandy.epq;

import java.util.ArrayList;

public class Category {

    private int category_id;
    private String category_name;
    private ArrayList<String> items;
    private boolean isExpanded = false;

    public Category(int category_id, String category_name, ArrayList<String> items) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.items = items;
    }

    public int getCategory_id() {
        return category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Category{" +
                "category_id=" + category_id +
                ", category_name='" + category_name + '\'' +
                ", items=" + items +
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
}
