package edu.gonzaga.utils;

public enum Suit {
    SPADES("Spade"),
    CLUBS("Club"),
    HEARTS("Heart"),
    DIAMONDS("Diamond");

    private final String value;

    Suit(String value) {
        this.value = value;
    }

    public String getString() {
        return this.value;
    }
}
