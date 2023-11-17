/* Class Name: Hand
 * Desc: Create and Manage Hands. A Hand has a boolean value to decide whether it shown (a listener will be watching to see if this changes)
 *       More importantly, Hand contains an array list of Cards that make up the hand. 
 *       A players hand should contain 2 cards. A hand for scoring should contain (5 or 7) cards.
 * Notes: *~*
 */
package edu.gonzaga.items;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;
    private boolean isVisible;

    /* Default Value Constructor For Deck
     * Creates the array list of cards and sets visibility to false by default
     */
    public Hand() { 
        cards = new ArrayList<Card>();
        isVisible = false;
    }

    /* Method Name: addCard()
     * Returns: N/A (Void)
     * Desc: Accepts a card and adds the given card to the cards arrayList
     * Events: A watcher should be watching hand to see when a card is added or removed
     */
    public void addCard(Card newCard) {
        this.cards.add(newCard);
    }

    /* Method Name: getCard()
     * Returns: Card
     * Desc: Accepts an index for hand and returns the card at the given index of the cards ArrayList
     * Events:
     */
    public Card getCard(int i) {
        return this.cards.get(i);
    }

    /* Method Name: setVisibilty()
     * Returns: N/A (Void) 
     * Desc: Accepts a boolean and sets isVisible to the given boolean value
     * Events: Watcher/listener should react to the change of isVisible
     */
    public void setVisibilty(boolean bool) {
        this.isVisible = bool;
    }

    /* Method Name: getVisibility()
     * Returns: A Boolean
     * Desc: returns the current boolean value of isVisible
     * Events: N/A
     */
    public boolean getVisibility() {
        return this.isVisible;
    }
}
