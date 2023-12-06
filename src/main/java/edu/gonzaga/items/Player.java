/** Class Name: Player
 *  Desc: This class creates and manages players! Players have a name, a set of chips, and 2 cards in their hand
 *        Player Methods are used to get and set names and card values, as well as get and manipulate the players chips
 *  Notes: 'o'
 */
package edu.gonzaga.items;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player {
    private String name;
    private Card c1;
    private Card c2;
    private BufferedImage icon;
    private final Scorer scorer;

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
        this.scorer = new Scorer();
    }
    
    /* Method Name: addCardListToScorer()
     * Returns: N/A
     * Desc: Adds an inputed card to players scorer
     * Events: N/A
     */
    public void addCardListToScorer(ArrayList<Card> newCards) {
        this.scorer.addCardListToHand(newCards);
    }

    /* Method Name: addHandToScorer()
     * Returns: N/A
     * Desc: Adds an players c1 and c2 to players scorer
     * Events: N/A
     */
    public void addHandToScorer() {
        this.scorer.addCardtoHand(c1);
        this.scorer.addCardtoHand(c2);
    }

    /* Method Name: runScorerChecks()
     * Returns: N/A
     * Desc: calls players scorer.runChecks()
     * Events: N/A
     */
    public void runScorerChecks() {
        this.scorer.runChecks();
    }

    /* Method Name: callCompareScores()
     * Returns: Integer
     * Desc: calls players scorer.compareScores(), accepts another players scorer to do so. Returns 0, 1, or -1
     * Events: N/A
     */
    public Integer callCompareScores(Scorer otherScorer) {
        return this.scorer.compareScores(otherScorer);
    }

    /* Method Name: getScorer()
     * Returns: Scorer
     * Desc: returns this.scorer
     * Events: N/A
     */
    public Scorer getScorer() {
        return this.scorer;
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

    public void addToChips(Integer chipsToAdd) {
        this.chips = this.chips + chipsToAdd;
    }

    public int getEscrowChips() {
        return this.escrowChips;
    }

    @SuppressWarnings("unused")
    public void setChips(int chips) {
        this.chips = chips;
    }

    /**
     * @return false if amt is invalid
     */
    private boolean decrementChips(int amt) {
        if (amt < 0) return false;
        if (amt > this.chips) {
            this.chips = 0;
            return true;
        }
        this.chips -= amt;
        return true;
    }

    /**
     * @return false if amt is invalid
     */
    @SuppressWarnings("unused")
    private boolean incrementChips(int amt) {
        if (amt <= 0) return false;
        this.chips += amt;
        return true;
    }

    /**
     * @return false if amt is invalid
     */
    @SuppressWarnings("unused")
    private boolean decrementEscrow(int amt) {
        if (amt <= 0) return false;
        if (amt > this.escrowChips) return false;
        this.escrowChips -= amt;
        return true;
    }

    /**
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

    public void resetEscrowChips() {
        this.escrowChips = 0;
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
