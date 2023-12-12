/** Class Name: Player
 *  Desc: This class creates and manages players! Players have a name, a set of chips, and 2 cards in their hand
 *        Player Methods are used to get and set names and card values, as well as get and manipulate the players chips
 *  Notes: Documented by McEwan Bain
 */
package edu.gonzaga.items;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player {
    private String name; // The player's name
    private Card c1; // The player's first card
    private Card c2; // The player's second card
    private BufferedImage icon; // The icon associated with player
    private final Scorer scorer; 

    private int escrowChips = 0; // The chips which player has bet this round of betting

    private boolean isAllIn = false; // Boolean value for whether the player has gone all in

    private int chips; // The player's current chips

    private boolean isFolded = false; // Boolean value for whether the player has folded

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

    /* Method Name: isFolded()
     * Returns: isFolded
     * Desc: Returns boolean value this.isFolded representing if the player is folded.
     * Events: N/A
     */
    public boolean isFolded() {
        return this.isFolded;
    }

    /* Method Name: setFolded()
     * Returns: N/A
     * Desc: Accepts a boolean isFolded and sets this.isFolded equal to this value.
     * Events: N/A
     */
    public void setFolded(boolean isFolded) {
        this.isFolded = isFolded;
    }

    
    /* Method Name: addCardListToScorer()
     * Returns: N/A
     * Desc: Adds an inputed card to players scorer.
     * Events: N/A
     */
    public void addCardListToScorer(ArrayList<Card> newCards) {
        this.scorer.addCardListToHand(newCards);
    }

    /* Method Name: addHandToScorer()
     * Returns: N/A
     * Desc: Adds an players c1 and c2 to players scorer.
     * Events: N/A
     */
    public void addHandToScorer() {
        this.scorer.addCardtoHand(c1);
        this.scorer.addCardtoHand(c2);
    }

    /* Method Name: runScorerChecks()
     * Returns: N/A
     * Desc: calls players scorer.runChecks().
     * Events: N/A
     */
    public void runScorerChecks() {
        this.scorer.runChecks();
    }

    /* Method Name: callCompareScores()
     * Returns: Integer
     * Desc: calls players scorer.compareScores(), accepts another players scorer to do so. Returns 0, 1, or -1.
     * Events: N/A
     */
    public Integer callCompareScores(Scorer otherScorer) {
        return this.scorer.compareScores(otherScorer);
    }

    /* Method Name: getScorer()
     * Returns: Scorer
     * Desc: returns this.scorer.
     * Events: N/A
     */
    public Scorer getScorer() {
        return this.scorer;
    }

    /* Method Name: getName()
     * Returns: A String
     * Desc: returns this players name as a string.
     * Events: N/A
     */
    public String getName() {
        return name;
    }

    /* Method Name: setName()
     * Returns: N/A (Void)
     * Desc: Accepts a String newName and sets Player's name = newName.
     * Events: Watcher should be looking at players name and update when player name is changed.
     */
    public void setName(String newName) {
        this.name = newName;
    } 

    /* Method Name: setCards()
     * Returns: N/A (Void)
     * Desc: Accepts two cards as inputs and sets player c1 and c2 equal to those two cards (Respectively).
     * Events: Watcher should be paying attention to players cards and when they change.
     */
    public void setCards(Card c1, Card c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    /* Method Name: getCardOne()
     * Returns: A Card
     * Desc: Returns the current value of this.c1.
     * Events: N/A
     */
    public Card getCardOne() {
        return c1;
    }

    /* Method Name: getCard2()
     * Returns: A Card
     * Desc: Returns the current value of this.c2.
     * Events: N/A
     */
    public Card getCardTwo() {
        return c2;
    }

    /* Method Name: getChips()
     * Returns: The player's chips.
     * Desc: Returns the integer this.chips, representing the player's chips.
     * Events: N/A
     */
    public int getChips() {
        return this.chips;
    }

    /* Method Name: addToChips()
     * Returns: N/A (void)
     * Desc: Accepts integer chipsToAdd and adds this value to this.chips.
     * Events: N/A
     */
    public void addToChips(Integer chipsToAdd) {
        this.chips = this.chips + chipsToAdd;
    }

    /* Method Name: getEscrowChips()
     * Returns: The player's escrow chips
     * Desc: Returns this.escrowChips, the value of chips which the player has already bet.
     * Events: N/A
     */
    public int getEscrowChips() {
        return this.escrowChips;
    }

    /* Method Name: setChips()
     * Returns: N/A (void)
     * Desc: Accepts integer chips and adds to this.chips, the player's chips. 
     * Events: N/A
     */
    @SuppressWarnings("unused")
    public void setChips(int chips) {
        this.chips = chips;
    }

    /* Method Name: decrementChips()
     * Returns: False if amt is not valid. 
     * Desc: Accepts integer amt, and attempts to subtract this amount from chips. 
     * Events: N/A
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

    /* Method Name: incrementChips()
     * Returns: False if amt is not valid. 
     * Desc: Accepts integer amt, and attempts to increase chips by this amount. 
     * Events: N/A
     */
    @SuppressWarnings("unused")
    private boolean incrementChips(int amt) {
        if (amt <= 0) return false;
        this.chips += amt;
        return true;
    }

    /* Method Name: decrementEscrow()
     * Returns: False if amt is not valid. 
     * Desc: Accepts integer amt, and attempts to subtract escrow by this amount. 
     * Events: N/A
     */
    @SuppressWarnings("unused")
    private boolean decrementEscrow(int amt) {
        if (amt <= 0) return false;
        if (amt > this.escrowChips) return false;
        this.escrowChips -= amt;
        return true;
    }

    /* Method Name: incrementEscrow()
     * Returns: False if amt is not valid. 
     * Desc: Accepts integer amt, and attempts to increment escrow by this amount. 
     * Events: N/A
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

    /* Method Name: incrementEscrowChips()
     * Returns: False if incrementEscrowChips() and decrementEscrowChips did not both pass.
     * Desc: Accepts an integer amt, incrementing the escrow and decrementing chips by this amount.
     *       If chips is equal to 0, put the player all in. 
     * Events: N/A
     */
    public boolean incrementEscrowChips(int amt)  {
        boolean increment = incrementEscrow(amt);
        boolean decrement = decrementChips(amt);
        if (this.chips == 0) {
            isAllIn = true;
            GameFrame.incrementAllIn();
        }
        return decrement && increment;
    }

    /* Method Name: resetEscrowChips()
     * Returns: N/A (void)
     * Desc: Sets the players escrowChips to 0.
     * Events: N/A
     */
    public void resetEscrowChips() {
        this.escrowChips = 0;
    }

    /* Method Name: isAllIn()
     * Returns: Whether the player is all-in.
     * Desc: Returns this.isAllIn, the boolean value of whether the player has gone all in.
     * Events: N/A
     */
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
     *  Returns: N/A (Void)
     *  Desc: Sets Players Icon
     */
    public void setIcon(BufferedImage newImage) {
        this.icon = newImage;
    }

    /* Method Name: resetRound()
     * Returns: N/A (void)
     * Desc: "Resets" the player in preparation for the next round by unfolding the player if they still have chips
     *        and disabling their all-in state.
     * Events: N/A
     */
    public void resetRound() {
        this.isFolded = this.chips == 0;
        this.isAllIn = false;
    }

}
