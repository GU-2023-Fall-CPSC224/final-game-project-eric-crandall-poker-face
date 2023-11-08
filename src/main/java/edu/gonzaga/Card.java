package edu.gonzaga;

import edu.gonzaga.utils.FaceValue;
import edu.gonzaga.utils.Suit;

public class Card {
    private FaceValue faceValue;
    private Suit suit;

    private final FaceValue DEFAULT_FACE_VALUE = FaceValue.ACE;
    private final Suit DEFAULT_SUIT = Suit.SPADES;

    public Card() {
        this.faceValue = DEFAULT_FACE_VALUE;
        this.suit = DEFAULT_SUIT;
    }

    public FaceValue getFaceValue() {
        return faceValue;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setFaceValue(FaceValue faceValue) {
        this.faceValue = faceValue;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }
}
