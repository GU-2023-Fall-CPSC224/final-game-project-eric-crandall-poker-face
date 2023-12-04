/** Class Name: Player
 *  Desc: This class creates and manages players! Players have a name, a set of chips, and 2 cards in their hand
 *        Player Methods are used to get and set names and card values, as well as get and manipulate the players chips
 *  Notes: 'o'
 */
package edu.gonzaga.items;

import java.awt.image.BufferedImage;
import edu.gonzaga.utils.PlayerIcons;

public class Player {
    private String name;
    private Card c1;
    private Card c2;
    private BufferedImage icon;

    private int escrowChips = 0;

    private boolean isAllIn = false;

    private int chips;

    private boolean isFolded = false;


    public boolean isFolded() {
        return this.isFolded;
    }

    public void setFolded(boolean isFolded) {
        this.isFolded = isFolded;
    }

    /* Default Value Constructor For Player
     * By default, sets players name to "Unknown Player", and creates new cards and chips for player.
     */
    public Player() {
        this.name = "Uknown Player";
        this.c1 = new Card();
        this.c2 = new Card();
        this.chips = 100;
        this.icon = null;
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

    public int getChips() {
        return this.chips;
    }

    public int getEscrowChips() {
        return this.escrowChips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    /**
     *
     * @param amt
     * @return false if amt is invalid
     */
    private boolean decrementChips(int amt) {
        if (amt < 0) return false;
        if (amt > this.chips) {
            this.chips = 0;
            return true;
        };
        this.chips -= amt;
        return true;
    }

    /**
     *
     * @param amt
     * @return false if amt is invalid
     */
    private boolean incrementChips(int amt) {
        if (amt <= 0) return false;
        this.chips += amt;
        return true;
    }

    /**
     *
     * @param amt
     * @return false if amt is invalid
     */
    private boolean decrementEscrow(int amt) {
        if (amt <= 0) return false;
        if (amt > this.escrowChips) return false;
        this.escrowChips -= amt;
        return true;
    }

    /**
     *
     * @param amt
     * @return false if amt is invalid
     */
    private boolean incrementEscrow(int amt) {
        if (amt < 0) return false;
        if (this.chips < amt) {
            this.escrowChips += this.chips;
            return true;
        }
        this.escrowChips += amt;
        return true;
    }

    public boolean incrementEscrowChips(int amt)  {
        boolean increment = incrementEscrow(amt);
        boolean decrement = decrementChips(amt);
        if (this.chips == 0) {
            isAllIn = true;
            GameFrame.incrementAllIn();
        }
        return decrement && increment;
    }

    public boolean isAllIn() {
        return this.isAllIn;
    }

    /* Method Name: getIcon()
     * Returns: BufferedImage
     * Desc: Returns Players Icon
     */
    public BufferedImage getIcon() {
        return this.icon;
    }

     /* Method Name: setIcon()
     * Returns: N/A (Void)
     * Desc: Sets Players Icon
     */
    public void setIcon(BufferedImage newImage) {
        this.icon = newImage;
    }

    public void resetRound() {
        this.isFolded = this.chips == 0;
        this.isAllIn = false;
    }

}
