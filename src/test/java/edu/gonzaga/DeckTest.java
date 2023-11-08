package edu.gonzaga;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeckTest {
    @Test
    void testDeckInitialization() {
        Deck d = new Deck();
        Card c = d.drawCard();

        String expectedValue = "A";
        String actualValue = c.getFaceValue();

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void testShuffle() {
        Deck d = new Deck();
        d.shuffle();

        Card c = d.drawCard();

        Boolean expectedValue = false;
        Boolean actualValue = (c.getFaceValue() == "A") && (c.getSuit() == "Spades");
        Assertions.assertEquals(expectedValue, actualValue);
    }
}
