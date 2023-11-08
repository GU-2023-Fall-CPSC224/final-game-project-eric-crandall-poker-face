package edu.gonzaga.utils;

public enum FaceValue {
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("J"),
    QUEEN("Q"),
    KING("K"),
    ACE("A"),
    JOKER("O");

    private final String value;

    FaceValue(String value) {
        this.value = value;
    }

    public String getString() {
        return this.value;
    }
}