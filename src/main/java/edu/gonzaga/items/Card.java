/** Class Name: Card
 *  Desc: A card keeps track of a face value and a suit. Various member methods allow for the
 *        retrieval, manipulation, and comparison of card values 
 *  Notes: :P
 */
package edu.gonzaga.items;

public class Card {
    private FaceValue faceValue;
    private Suit suit;

    private static final FaceValue DEFAULT_FACE_VALUE = FaceValue.ACE;
    private static final Suit DEFAULT_SUIT = Suit.SPADES;

    /* Default Value Constructor for Card Class
     * Desc: Creates a card and sets its value by defualt to the Ace of Spades
     */
    public Card() {
        this.faceValue = DEFAULT_FACE_VALUE;
        this.suit = DEFAULT_SUIT;
    }

    /* Method Name: getFaceValue()
     * Returns: FaceValue
     * Desc: Returns cards current face value
     * Events:
     */
    public FaceValue getFaceValue() {
        return faceValue;
    }

    /* Method Name: getSuit()
     * Returns: Suit
     * Desc: Returns cards current Suit
     * Events:
     */
    public Suit getSuit() {
        return suit;
    }

    /* Method Name: setFaceValue()
     * Returns: N/A (Void)
     * Desc: Accepts a FaceValue and sets cards FaceValue to the given FaceValue
     * Events:
     */
    public void setFaceValue(FaceValue faceValue) {
        this.faceValue = faceValue;
    }

    /* Method Name: setSuit()
     * Returns: N/A (Void)
     * Desc: Accepts a suit and sets cards Suit value to the given Suit
     * Events:
     */
    public void setSuit(Suit suit) {
        this.suit = suit;
    }
}
