package edu.gonzaga;
import edu.gonzaga.items.Card;
import edu.gonzaga.items.Hand;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class HandTest {
    @Test
    void testAddandGetCard() {
        Card card = new Card();
        Hand hand = new Hand();
        hand.addCard(card);

        Card expected = card;
        Card actual = hand.getCard(0);
        assertEquals(expected, actual);
    }

    @Test
    void testGetVisibility() {
        Hand hand = new Hand();

        boolean actual = hand.getVisibility();
        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    void testSetVisibility() {
        Hand hand = new Hand();
        hand.setVisibilty(true);

        boolean actual = hand.getVisibility();
        boolean expected = true;
        assertEquals(expected, actual);
    }
}
