/** Class Name: Player
 *  Desc: This class creates and manages players! Players have a name, a set of chips, and 2 cards in their hand
 *        Player Methods are used to get and set names and card values, as well as get and manipulate the players chips
 *  Notes: 'o'
 */
package edu.gonzaga.items;

public class Player {
    private String name;
    private Chips chips;
    private Card c1;
    private Card c2;

    /* Default Value Constructor For Player
     * By default, sets players name to "Unknown Player", and creates new cards and chips for player.
     */
    public Player() {
        this.name = "Uknown Player";
        this.c1 = new Card();
        this.c2 = new Card();
        this.chips = new Chips();
    }
 
    /* Method Name: getName()
     * Returns: A String
     * Desc: returns this players name as a string
     * Events: N/A
     */
    public String getName() {
        return name;
    }

    /* Method Name: setName()
     * Returns: N/A (Void)
     * Desc: Accepts a String newName and sets Player's name = newName
     * Events: Watcher should be looking at players name and update when player name is changed
     */
    public void setName(String newName) {
        this.name = newName;
    } 

    /* Method Name: setCards()
     * Returns: N/A (Void)
     * Desc: Accepts two cards as inputs and sets player c1 and c2 equal to those two cards (Respectively)
     * Events: Watcher should be paying attention to players cards and when they change
     */
    public void setCards(Card c1, Card c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    /* Method Name: getCardOne()
     * Returns: A Card
     * Desc: Returns the current value of this.c1
     * Events: N/A
     */
    public Card getCardOne() {
        return c1;
    }

    /* Method Name: getCard2()
     * Returns: A Card
     * Desc: Returns the current value of this.c2
     * Events: N/A
     */
    public Card getCardTwo() {
        return c2;
    }

    //temporary for testing purposes
    public Integer getScore() {
        return chips.getTotalChipsValues();
    }
}
