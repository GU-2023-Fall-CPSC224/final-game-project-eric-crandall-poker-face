package edu.gonzaga;

import edu.gonzaga.utils.FaceValue;
import edu.gonzaga.utils.Suit;
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
}