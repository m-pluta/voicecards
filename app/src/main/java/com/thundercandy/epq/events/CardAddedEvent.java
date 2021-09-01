package com.thundercandy.epq.events;


import com.thundercandy.epq.data.Card;

public class CardAddedEvent {

    private static int category;
    private static int targetPosition;
    private static Card addedCard;

    public static int getCategory() {
        return category;
    }

    public static void setCategory(int category) {
        CardAddedEvent.category = category;
    }

    public static Card getAddedCard() {
        return addedCard;
    }

    public static void setAddedCard(Card addedCard) {
        CardAddedEvent.addedCard = addedCard;
    }

    public static int getTargetPosition() {
        return targetPosition;
    }

    public static void setTargetPosition(int targetPosition) {
        CardAddedEvent.targetPosition = targetPosition;
    }
}
