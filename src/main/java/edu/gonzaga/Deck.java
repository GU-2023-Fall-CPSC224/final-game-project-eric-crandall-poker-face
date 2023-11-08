package edu.gonzaga;

import java.util.ArrayList;
import java.util.Collections;
//TODO: default fill deck (final list of suits, and values possible for cards?)

public class Deck {
    private ArrayList<Card> cards;

    private final String[] suits = {"Spades", "Diamonds", "Clubs", "Hearts"};
    private final String[] faceValues = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    
    public Deck() {
        cards = new ArrayList<>(); 
        createDeck();
    }

    public void createDeck() {
        for (Integer i = 0; i < suits.length; i++) {
            for (Integer j = 0; j < faceValues.length; j++) {
                Card c = new Card();

                c.setSuit(suits[i]);
                c.setFaceValue(faceValues[j]);

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
