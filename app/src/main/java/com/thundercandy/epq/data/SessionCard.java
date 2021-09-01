package com.thundercandy.epq.data;

public class SessionCard extends Card {

    int timesUnknown = 0;
    boolean known = false;

    public SessionCard(int id, String term, String definition, int timesUnknown, boolean known) {
        super(id, term, definition);
        this.timesUnknown = timesUnknown;
        this.known = known;
    }

    public int getTimesUnknown() {
        return timesUnknown;
    }

    public void setTimesUnknown(int timesUnknown) {
        this.timesUnknown = timesUnknown;
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }
}
