package edu.gonzaga;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CardTest {
    @Test
    void testDefaultFaceValue() {
        Card c = new Card();

        String expectedValue = "A";
        String actualValue = c.getFaceValue();

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void testDefaultSuit() {
        Card c = new Card();

        String expectedValue = "Spades";
        String actualValue = c.getSuit();

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void testAssignFaceValue() {
        Card c = new Card();

        c.setFaceValue("K");

        String expectedValue = "K";
        String actualValue = c.getFaceValue();

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void testAssignSuit() {
        Card c = new Card();

        c.setSuit("Diamonds");

        String expectedValue = "Diamonds";
        String actualValue = c.getSuit();

        Assertions.assertEquals(expectedValue, actualValue);
    }
}
