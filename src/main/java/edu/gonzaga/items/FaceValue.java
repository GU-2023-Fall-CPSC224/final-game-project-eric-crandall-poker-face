package edu.gonzaga.items;

public enum FaceValue {
    BLANK(null), // Helper card to fix ordinal values to be accurate to card values (TWO.ordinal() + 1 = 2) aka "value = index + 1"
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

    public int getValue() {
        return ordinal() + 1;
    }
}
