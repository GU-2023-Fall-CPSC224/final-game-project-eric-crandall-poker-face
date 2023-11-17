/** Class Name: Player
 *  Desc: TODO: add desc
 *  Notes: 0.0
 */
package edu.gonzaga.items;

public class Player {
    private String name;
    private Chips chips;
    private Card c1;
    private Card c2;

    /* Default Value Constructor For Deck
     * Creates the array list of cards and adds the 52 unique cards to the deck
     */
    public Player() {
        this.name = "Uknown Player";
        this.chips = new Chips();
    }

    public String getName() {
        return name;
    }

    public void setCards(Card c1, Card c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    public Card getCardOne() {
        return c1;
    }

    public Card getCardTwo() {
        return c2;
    }

    //temporary for testing purposes
    public Integer getScore() {
        return chips.getTotalChipsValues();
    }
}
