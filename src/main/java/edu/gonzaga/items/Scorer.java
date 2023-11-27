/* Class Name: Scorer
 * Desc: The scorer class is used to manage scoring hands of cards. By default its designed to contain an
 *       ArrayList of 7 cards, two from a players hand and the other 5 from the community cards. 
 *       The scorer will automatically create the best 5 card hand possible from the cards its given.
 *       The scorer contains a precendence value (0-9) based on the possible hand combinations.
 *       The Scorer will also keep track of the High Card and *Special* Card for cases where 2 players have the same precedence
 *       Scorer's are also able to compare themselves with other scorers. 
 *       
 * Notes: *A *Special* Card is the highest card used in making a combintation (I.E. three in a pair of three's)
 */
package edu.gonzaga.items;

import java.util.ArrayList;

public class Scorer {
    private ArrayList<Card> hand;           // An array list of 7 cards (2 from player, 5 from community)
    private FaceValue highCardVal;          // Contains the face value of the highest card used in hand 
    private FaceValue specialCardVal;       // The face value of the highest card used in a specific combo (can be different that highCard)
    private Integer precedence;             // Precedence value of hand, higher precendence beats lower precedence.
    private Integer[] dupes;                //Has an index for each of the card values (2 = index 0, Ace = index 12) that contains how many times each card appears in hand

    /* Scorer Default Value Constructor
     * Creates an empty arraylist of cards, sets precedence to 0, and the face value for high/special cards to blank/NULL
     */
    public Scorer() {
        this.hand = new ArrayList<Card>();
        this.precedence = 0;
        this.highCardVal = FaceValue.BLANK;
        this.specialCardVal = FaceValue.BLANK;
        Integer temp[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.dupes = temp;
    }

    /* Method Name: countDupes()
     * Returns: N/A (Void)
     * Desc: Loops through hand and count how many times each face value appears, stores those values in dupes
     * Events: N/A
     */
    public void countDupes() {
        Integer temp[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        for(int i = 0; i < hand.size(); i++) {
            temp[this.hand.get(i).getFaceValue().getValue()-2]++; //minus 2 to get indexing to align with ordinal values
        }
        this.dupes = temp;
    }
    /* Method Name: addCardtoHand()
     * Returns: N/A (Void)
     * Desc: Accepts a card and adds it to hands arrayList of cards
     * Events: N/A
     */
    public void addCardtoHand(Card newCard) {
        this.hand.add(newCard);
    }

    /* Method Name: getHighCardVal()
     * Returns: A FaceValue
     * Desc: Returns this classes highCardValue
     * Events: N/A
     */
    public FaceValue getHighCardVal() {
        return highCardVal;
    }

    /* Method Name: sortHand();
     * Returns: N/A (Void)
     * Desc: Sorts hand array list based on the ordinal values of each cards face value (Bubble sort)
     * Events: N/A
     */
    public void sortHand() {
        boolean sorted = false, swapped = false;
        while (!sorted) {
            for(int i = 0; i < hand.size()-1; i++) {
                if(hand.get(i).getFaceValue().getValue() > hand.get(i+1).getFaceValue().getValue()) {
                    Card tempCard = hand.get(i);
                    hand.set(i, hand.get(i+1));
                    hand.set(i+1, tempCard);
                    swapped = true;
                }
            }
            if(!swapped) {
                sorted = true;
            } else {
                swapped = false;
            }
        }
    }

    /* Method Name: sortHandSuits();
     * Returns: N/A (Void)
     * Desc: Sorts hand array list based on the ordinal values of each cards Suit, in alphabetical order (Bubble sort)
     * Events: N/A
     */
    public void sortHandSuits() {
        boolean sorted = false, swapped = false;
        while (!sorted) {
            for(int i = 0; i < hand.size()-1; i++) {
                if(hand.get(i).getSuit().getString().charAt(0) > hand.get(i+1).getSuit().getString().charAt(0)) {
                    Card tempCard = hand.get(i);
                    hand.set(i, hand.get(i+1));
                    hand.set(i+1, tempCard);
                    swapped = true;
                }
            }
            if(!swapped) {
                sorted = true;
            } else {
                swapped = false;
            }
        }
    }

    /* Method Name: getHandAt()
     * Returns: A Card
     * Desc: Accepts an interger i and returns the card stored at index i of hand
     * Events: N/A
     */
    public Card getHandAt(int i) {
        return this.hand.get(i);
    }

/*********
 * 
 * Below this line are all the individual methods repsonsible for checking for different card combos.
 * They have been seperated for readability purposes
 * 
 *********/ 

    /* Method Name: checkHighCard()
     * Returns: Void (N/A) 
     * Desc: Loops through hand and checks for the sets the highest card to high card
     * Events: N/A
     */
    public void checkHighCard() {
        FaceValue highest = this.hand.get(0).getFaceValue();
        for(int i = 1; i < this.hand.size(); i++) { //starting i at 1 because highest should already be set to hand[0]
            if(this.hand.get(i).getFaceValue().getValue() > highest.getValue()) { //Compares the ordinal values of each face value
                highest = this.hand.get(i).getFaceValue();
            }
        }
        this.highCardVal = highest;
    }

    /* Method Name: check1Pair()
     * Returns: A Boolean confirming whether or not combo was found
     * Desc: Checks hand for a single pair of any face value
     * Events: N/A
     */
    public boolean check1Pair() {
        for(int i = 0; i < 13; i++) {
            if(this.dupes[i] == 2) {
                //TODO set special card
                this.precedence = 1;
                return true;
            }
        }
        return false;
    }

    /* Method Name: check2Pair()
     * Returns: A Boolean confirming whether or not combo was found
     * Desc:
     * Events: N/A
     */
    public boolean check2Pair() {
        boolean found1 = false;
        for(int i = 0; i < 13; i++) {
            if(this.dupes[i] == 2 && found1 == true) {
                //TODO set special card
                this.precedence = 2;
                return true;
            } else if(this.dupes[i] == 2) {
                found1 = true;
            }
        }
        return false;
    }

    /* Method Name: check3Kind()
     * Returns: A Boolean confirming whether or not combo was found
     * Desc: Checks hand for 3 of one face value
     * Events: N/A
     */
    public boolean check3Kind() {
        for(int i = 0; i < 13; i++) {
            if(this.dupes[i] == 3) {
                //TODO set special card
                this.precedence = 3;
                return true;
            }
        }
        return false;
    }

    /* Method Name: checkStraight()
     * Returns: A Boolean confirming whether or not combo was found
     * Desc: Checks hand to see if there are 5 face values in a row
     * Events: N/A
     */
    public boolean checkStraight() {
        Integer numInRow = 0;
        for(int i = 0; i < 13; i++) {
            if(dupes[i] > 0) {
                numInRow++;
                if(numInRow == 5) {
                    //TODO add special card val
                    this.precedence = 4;
                    return true;
                }
            } else {
                numInRow = 0;
            }
        }
        return false;
    }

    /* Method Name: checkFlush()
     * Returns: A Boolean confirming whether or not combo was found
     * Desc: Checks hand to see if aany 5 cards in hand are off the same suit
     * Events: N/A
     */
    public boolean checkFlush() {
        Integer numEachSuit[] = {0, 0, 0, 0}; //indexing: Hearts, Diamons, Spades, Clubs in that order
        for(int i = 0; i < hand.size(); i++){
            Suit temp = hand.get(i).getSuit();
            if(temp == Suit.HEARTS) {
                numEachSuit[0]++;
            } else if(temp == Suit.DIAMONDS) {
                numEachSuit[1]++;
            } else if(temp == Suit.SPADES) {
                numEachSuit[2]++;
            } else {
                numEachSuit[3]++;
            }
        }

        for(int i = 0; i < 4; i++) {
            if(numEachSuit[i] >= 5) {
                this.precedence = 5;
                return true;
            }
        }
        return false;
    }

    /* Method Name: checkFullHouse()
     * Returns: A Boolean confirming whether or not combo was found 
     * Desc: Checks if there is both a 3 of a kind and a pair in hand
     * Events: N/A
     */
    public boolean checkFullHouse() {
        //pair and trips will be set to true if a pair or a triple is found
        boolean pair = false;
        boolean trips = false;

        for(int i = 0; i < 13; i++) {
            if(dupes[i] == 3 && trips == false) { //There will be some cases where there a two trips in hand instead of a pair and a trips. this check is to account for that case.
                trips = true;
            } else if (dupes[i] >= 2) {
                pair = true;
            }
        }
        if(pair && trips) {
        //TODO Special card values
            this.precedence = 6;
            return true;
        }
        return false;
    }

    /* Method Name: check4Kind()
     * Returns: A Boolean confirming whether or not combo was found 
     * Desc: Loops through dupes and checks if there are any face values that appear 4 times
     * Events: N/A
     */
    public boolean check4Kind() {
        for(int i = 0; i < 13; i++) {
            if(this.dupes[i] == 4) {
                //TODO set special card
                this.precedence = 7;
                return true;
            }
        }
        return false;
    }

    /* Method Name: checkStraightFlush()
     * Returns: A Boolean confirming whether or not combo was found 
     * Desc: Checks hand for 5 cards in a numerical sequence all with the same suit
     * Events: N/A
     */
    public boolean checkStraightFlush() {
        Integer numInRow = 1;
        //First check if theres a straight and a flush in hand, if one doesnt appear it cant be a straight flush
        if(! this.checkStraight() || !this.checkFlush()){
            return false;
        }
        //Sort hand
        this.sortHand();

        Suit currSuit = hand.get(0).getSuit();
        Integer prevFaceValue = hand.get(0).getFaceValue().getValue();
        for(int i = 1; i < hand.size(); i++) {
            if(hand.get(i).getSuit() == currSuit && hand.get(i).getFaceValue().getValue() == prevFaceValue + 1) {
                prevFaceValue++;
                numInRow++;
                if(numInRow == 5) {
                    //TODO set special card value
                    this.precedence = 8;
                    return true;
                }
            } else {
                currSuit = hand.get(i).getSuit();
                prevFaceValue = hand.get(i).getFaceValue().getValue();
                numInRow = 1;
            }
        }
        return false;
    }

    /* Method Name: checkRoyalFlush()
     * Returns: A Boolean confirming whether or not combo was found 
     * Desc: Checks hand for a combo of 10, J, Q, K, A all of the same suit.
     * Events: N/A
     */
    public boolean checkRoyalFlush() {
        Suit suits[] = {Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS, Suit.SPADES, Suit.CLUBS}; //These are all placeholder suit values
        //Check to see that 10-A are in hand
        for(int i = 8; i < 13; i++) { //index 8 represents 10 in dupes
            if(this.dupes[i] == 0) {
                return false;
            }
        }

        //sorting hand by grouping suits and numerical values
        this.sortHand();
        this.sortHandSuits();

        //Checking suit values of 10-A
        for(int i = 0; i < hand.size(); i++) {
            if(this.hand.get(i).getFaceValue() == FaceValue.TEN) {
                suits[0] = this.hand.get(i).getSuit();
            } else if(this.hand.get(i).getFaceValue() == FaceValue.JACK) {
                suits[1] = this.hand.get(i).getSuit();
            } else if(this.hand.get(i).getFaceValue() == FaceValue.QUEEN) {
                suits[2] = this.hand.get(i).getSuit();
            } else if(this.hand.get(i).getFaceValue() == FaceValue.KING) {
                suits[3] = this.hand.get(i).getSuit();
            } else if(this.hand.get(i).getFaceValue() == FaceValue.ACE) {
                suits[4] = this.hand.get(i).getSuit();
            
                if(suits[0] == suits[1] && suits[1] == suits[2]  && suits[2] == suits[3] && suits[3] == suits[4]) {
                    this.precedence = 9;
                    return true;
                    //No special card value needed
                }
            }
        }
        return false;
    }

}
