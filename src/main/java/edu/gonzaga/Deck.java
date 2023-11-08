package edu.gonzaga;

import edu.gonzaga.utils.FaceValue;
import edu.gonzaga.utils.Suit;

import java.util.ArrayList;
import java.util.Collections;
//TODO: default fill deck (final list of suits, and values possible for cards?)

public class Deck {
    private final ArrayList<Card> cards;


    public Deck() {
        cards = new ArrayList<>(); 
        createDeck();
    }

    public void createDeck() {
        for (int i = 0; i < Suit.values().length; i++) {
            for (int j = 0; j < FaceValue.values().length - 1; j++) {
                Card c = new Card();

                c.setSuit(Suit.values()[i]);
                c.setFaceValue(FaceValue.values()[j]);

                cards.add(c);
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.remove(0);
    }
}
