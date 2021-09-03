package com.thundercandy.epq;

import android.util.Log;

import com.thundercandy.epq.data.SessionCard;

import java.util.ArrayList;
import java.util.Collections;

public class SessionManager {

    public static final double learntThreshold = 0.9;   //TODO: Fetch these values form sharedPreferences
    public static final double seenThreshold = 3;

    onEventListener listener;
    ArrayList<SessionCard> cards;
    boolean ended = false;

    SessionCard loadedCard;
    int lastCardID = -1;
    boolean definitionRevealed = false;

    public SessionManager(ArrayList<SessionCard> cards) {
        this.listener = null;
        this.cards = cards;
    }

    public void start() {
        if (listener != null) { // Fire listener
            listener.onStart();
        }
        loadNextCard();
    }

    public void end() {
        if (listener != null && !ended) { // Fire listener
            listener.onEnded(cards);
        }
        ended = true;
    }

    public boolean isEnded() {
        return ended;
    }

    // Logs the state of the cards arraylist
    private void logData() {
        String result = "cards={";
        for (SessionCard sc : cards) {
            result += sc.toShortString() + ", ";
        }
        result += "}";

        Log.d("SESSION_END", result);
    }

    //When the current loadedCard is known by the user
    public void known() {
        loadedCard.known();
        afterChoice();
    }

    //When the current loadedCard is not known by the user
    public void unknown() {
        loadedCard.unknown();
        afterChoice();
    }

    //This method is called after a user has made a choice
    public void afterChoice() {
        if (checkSessionEnd()) {
            end();
        } else {
            Collections.rotate(cards, -1);
            loadNextCard();
        }
    }

    public void revealDefinition() {
        if (listener != null) { // Fire listener
            listener.onDefinitionRevealed(loadedCard.getDefinition());
        }
        definitionRevealed = true;
    }

    int cardsCycled = 0;

    public void loadNextCard() {
        if (loadedCard != null) {               // Saves the previously loaded card's id
            lastCardID = loadedCard.getId();
        }
        if (cardsCycled == cards.size()) {      // If the user has gone through the entire arraylist of cards, it shuffles the deck
            Collections.shuffle(cards);
            cardsCycled = 0;
        }
        definitionRevealed = false;

        if (cards.get(0).getId() == lastCardID) {               // So the user doesn't see the same card two times in a row
            Collections.swap(cards, 0, cards.size() - 1);   // Swaps the first and last card
        }

        loadedCard = cards.get(0);          // Gets the next card and cycles through the cards arraylist
        cardsCycled++;

        if (listener != null) { // Fire listener
            listener.onReady(loadedCard.getTerm());
        }
    }

    // Checks the whether the session should end or not
    public boolean checkSessionEnd() {
        boolean outcome = true;
        for (SessionCard sc : cards) {
            if (!sc.isLearnt() || sc.getTimesSeen() < seenThreshold) {
                outcome = false;
                break;
            }
        }
        return outcome;
    }

    public void setOnEventListener(onEventListener listener) {
        this.listener = listener;
    }

    public interface onEventListener {
        public void onStart();

        public void onEnded(ArrayList<SessionCard> cards);

        public void onDefinitionRevealed(String definition);

        public void onReady(String term);
    }

}
