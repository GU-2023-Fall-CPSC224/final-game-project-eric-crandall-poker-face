package edu.gonzaga.items;

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
            // j = 1 to skip blank, length = length - 1 to skip joker
            for (int j = 1; j < FaceValue.values().length - 1; j++) {
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
