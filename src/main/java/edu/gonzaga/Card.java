package edu.gonzaga;

import edu.gonzaga.utils.Suit;

public class Card {
    private String faceValue;
    private Suit suit;

    private final String DEFAULT_FACE_VALUE = "A";
    private final Suit DEFAULT_SUIT = Suit.SPADES;

    public Card() {
        this.faceValue = DEFAULT_FACE_VALUE;
        this.suit = DEFAULT_SUIT;
    }

    public String getFaceValue() {
        return faceValue;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setFaceValue(String faceValue) {
        this.faceValue = faceValue;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }
}
