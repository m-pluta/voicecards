package com.thundercandy.epq.data;

import static com.thundercandy.epq.SessionManager.learntThreshold;
import static com.thundercandy.epq.SessionManager.seenThreshold;

public class SessionCard extends Card {

    int timesSeen = 0;
    int timesKnown = 0;
    boolean learnt = false;

    public SessionCard(int id, String term, String definition) {
        super(id, term, definition);
    }

    public boolean isLearnt() {
        return learnt;
    }

    public void learnt() {
        this.learnt = true;
    }

    public double getSuccessRate() {
        int success = this.getTimesKnown();
        int total = this.getTimesSeen();
        if (total == 0) {
            return -1;
        } else {
            return (double) success / total;
        }

    }

    public void checkLearnt() {
        if (this.getSuccessRate() >= learntThreshold && this.timesSeen >= seenThreshold) {
            this.learnt();
        }
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
        checkLearnt();
    }

    public void unknown() {
        seen();
        checkLearnt();
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

    public String toShortString() {
        return "SessionCard{" +
                "id=" + this.getId() +
                ", term=" + this.getTerm() +
                ", timesSeen=" + timesSeen +
                ", timesKnown=" + timesKnown +
                ", timesUnknown=" + (timesSeen - timesKnown) +
                '}';
    }
}