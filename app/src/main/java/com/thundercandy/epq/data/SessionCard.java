package com.thundercandy.epq.data;

public class SessionCard extends Card {

    int timesSeen = 0;
    int timesKnown = 0;

    public SessionCard(int id, String term, String definition) {
        super(id, term, definition);
    }

    public int getTimesSeen() {
        return timesSeen;
    }

    public int getTimesKnown() {
        return timesKnown;
    }

    public int getTimesUnknown() {
        return timesSeen - timesKnown;
    }

    public void known() {
        seen();
        timesKnown++;
    }

    public void unknown() {
        seen();
    }

    public void seen() {
        timesSeen++;
    }

    @Override
    public String toString() {
        return "SessionCard{" +
                "id=" + this.getId() +
                ", term=" + this.getTerm() +
                ", definition=" + this.getDefinition() +
                ", timesSeen=" + timesSeen +
                ", timesKnown=" + timesKnown +
                ", timesUnknown=" + (timesSeen - timesKnown) +
                '}';
    }
}
