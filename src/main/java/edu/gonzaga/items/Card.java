package edu.gonzaga.items;

public class Card {
    private FaceValue faceValue;
    private Suit suit;

    private static final FaceValue DEFAULT_FACE_VALUE = FaceValue.ACE;
    private static final Suit DEFAULT_SUIT = Suit.SPADES;

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
