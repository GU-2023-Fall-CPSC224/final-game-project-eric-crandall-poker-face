package edu.gonzaga;

import edu.gonzaga.items.Card;
import edu.gonzaga.items.FaceValue;
import edu.gonzaga.items.Suit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CardTest {
    @Test
    void testDefaultFaceValue() {
        Card c = new Card();

        FaceValue expectedValue = FaceValue.ACE;
        FaceValue actualValue = c.getFaceValue();

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void testDefaultSuit() {
        Card c = new Card();

        Suit expectedValue = Suit.SPADES;
        Suit actualValue = c.getSuit();

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void testAssignFaceValue() {
        Card c = new Card();

        c.setFaceValue(FaceValue.KING);

        FaceValue expectedValue = FaceValue.KING;
        FaceValue actualValue = c.getFaceValue();

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void testAssignSuit() {
        Card c = new Card();

        c.setSuit(Suit.DIAMONDS);

        Suit expectedValue = Suit.DIAMONDS;
        Suit actualValue = c.getSuit();

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void testOrdinalValueAce() {
        FaceValue ace = FaceValue.ACE;
        int expected = 14;
        int actual = ace.getValue();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testOrdinalValueTwo() {
        FaceValue two = FaceValue.TWO;
        int expected = 2;
        int actual = two.getValue();

        Assertions.assertEquals(expected, actual);
    }
}
