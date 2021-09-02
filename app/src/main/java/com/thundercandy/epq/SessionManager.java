package com.thundercandy.epq;

import android.util.Log;

import com.thundercandy.epq.data.SessionCard;

import java.util.ArrayList;
import java.util.Collections;

public class SessionManager {

    public static final double learntThreshold = 0.9;   //TODO: Fetch these values form sharedPreferences
    public static final double seenThreshold = 3;

    private final SessionActivity sessionActivity;
    ArrayList<SessionCard> cards;
    boolean definitionRevealed = false;
    SessionCard loadedCard;
    boolean ended = false;
    int lastCardID = -1;

    public SessionManager(SessionActivity sessionActivity, ArrayList<SessionCard> cards) {
        this.sessionActivity = sessionActivity;
        this.cards = cards;
    }

    public void start() {
        sessionActivity.btnEndSession.setEnabled(true);
        sessionActivity.btnKnown.setEnabled(true);
        sessionActivity.btnUnknown.setEnabled(true);
        loadNextCard();
    }

    public void end() {
        ended = true;
        logData();
    }

    public boolean isEnded() {
        return ended;
    }

    private void logData() {
        String result = "cards={";
        for (SessionCard sc : cards) {
            result += sc.toShortString() + ", ";
        }
        result += "}";

        Log.d("SESSION_END", result);
    }

    public void known() {
        loadedCard.known();
        afterChoice();
    }

    public void unknown() {
        loadedCard.unknown();
        afterChoice();
    }

    public void afterChoice() {
        if (checkSessionEnd()) {
            end();
        } else {
            Collections.rotate(cards, -1);
            loadNextCard();
        }
    }

    public void revealDefinition() {
        sessionActivity.txtDefinition.setText(loadedCard.getDefinition());
        definitionRevealed = true;
        sessionActivity.btnKnown.setEnabled(true);
        sessionActivity.btnUnknown.setEnabled(true);
    }

    int cardsCycled = 0;

    public void loadNextCard() {
        if (loadedCard != null) {
            lastCardID = loadedCard.getId();
        }
        if (cardsCycled == cards.size()) {
            Collections.shuffle(cards);
            cardsCycled = 0;
        }

        definitionRevealed = false;
        sessionActivity.btnKnown.setEnabled(false);     //TODO: replace this with grey buttons or something
        sessionActivity.btnUnknown.setEnabled(false);


        if (cards.get(0).getId() == lastCardID) {               // So the user doesn't see the same card two times in a row
            Collections.swap(cards, 0, cards.size() - 1);
        }
        loadedCard = cards.get(0);
        cardsCycled++;

        sessionActivity.txtTerm.setText(loadedCard.getTerm());
        sessionActivity.txtDefinition.setText("Click to see definition");
    }

    public boolean checkSessionEnd() {
        boolean outcome = true;
        for (SessionCard sc : cards) {
            if (!sc.isLearnt() || sc.getTimesSeen() < seenThreshold) {
                outcome = false;
                break;
            }
        }
        logData();
        Log.d("outcome", String.valueOf(outcome));
        return outcome;
    }

}
