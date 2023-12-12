/**
 * Class Name: Deck
 * Desc: Deck contains an array list of Cards. By default, deck has 52 cards (1 of each different faceValue and suit combo)
 * Methods are used to draw cards from deck, shuffle deck, create a new deck, etc
 * Notes: Documented by McEwan Bain
 */
package edu.gonzaga.items;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private final ArrayList<Card> cards;

    /* Default Value Constructor For Deck
     * Creates the array list of cards and adds the 52 unique cards to the deck
     */
    public Deck() {
        cards = new ArrayList<>();
        createDeck();
    }

    /* Method Name: createDeck()
     * Returns: N/A (Void)
     * Desc: Loops through the 4 suit values and 13 possible card values and creates new cards w/ those unique values
     *       Then adds those newly created cards to decks arrayList of cards
     * Events:
     */
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

    /* Method Name: shuffle()
     * Returns: N/A (Void)
     * Desc: Calls collections.shuffle() on cards to shuffle(reorder) the deck of cards
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /* Method Name: drawCard()
     * Returns: Top [Card] on deck
     * Desc: Removes the card at cards[0] and saves it to new Card drawn. Returns the drawn card
     * Events:
     */
    public Card drawCard() {
        Card drawn = cards.get(0);
        cards.remove(0);
        return drawn;
    }
}
