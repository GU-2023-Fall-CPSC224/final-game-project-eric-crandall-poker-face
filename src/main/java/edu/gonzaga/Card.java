package edu.gonzaga;

//TODO: handle assigning invalid suits
public class Card {
    private String faceValue;
    private String suit;

    private final String DEFAULT_FACE_VALUE = "A";
    private final String DEFAULT_SUIT = "Spades";

    public Card() {
        this.faceValue = DEFAULT_FACE_VALUE;
        this.suit = DEFAULT_SUIT;
    }

    public String getFaceValue() {
        return faceValue;
    }

    public String getSuit() {
        return suit;
    }

    public void setFaceValue(String faceValue) {
        this.faceValue = faceValue;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }
}
