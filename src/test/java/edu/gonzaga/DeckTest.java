package edu.gonzaga;

import edu.gonzaga.items.Card;
import edu.gonzaga.items.Deck;
import edu.gonzaga.items.FaceValue;
import edu.gonzaga.items.Suit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeckTest {
    @Test
    void testDeckInitialization() {
        Deck d = new Deck();
        Card c = d.drawCard();

        // Ace value > 2 Value. Ace high, so ace should insert last in the deck as it's ordinal value is 14
        // FaceValue expectedValue = FaceValue.ACE;

        FaceValue expectedValue = FaceValue.TWO;
        FaceValue actualValue = c.getFaceValue();

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void testShuffle() {
        Deck d = new Deck();
        d.shuffle();

        Card c = d.drawCard();

        Boolean expectedValue = false;
        Boolean actualValue = (c.getFaceValue() == FaceValue.ACE) && (c.getSuit() == Suit.SPADES);
        Assertions.assertEquals(expectedValue, actualValue);
    }
}
