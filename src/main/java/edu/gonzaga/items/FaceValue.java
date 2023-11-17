/** Enum Name: FaceValue
 *  Desc: Enumerated Values used to deal with the face calues of cards
 *        Methods are used to get the value and strings of a FaceValue
 *  Notes: :]
 */
package edu.gonzaga.items;

public enum FaceValue {
    BLANK(null), // Helper card to fix ordinal values to be accurate to card values (TWO.ordinal() + 1 = 2) aka "value = index + 1"
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("J"),
    QUEEN("Q"),
    KING("K"),
    ACE("A"),
    JOKER("O");

    private final String value;

    /* Contrcutor for FaceValue
     * Accepts a string and sets FaceValues value to that string
     */
    FaceValue(String value) {
        this.value = value;
    }

    /* Method Name: toString()
     * Returns: A String
     * Desc: Returns the string value tied to a faceValue
     */
    @Override
    public String toString() {
        return this.value;
    }

    /* Method Name: getValue()
     * Returns: An Int
     * Desc: uses ordinal values to enumerate card values and returns the proper numeric value of a card
     */
    public int getValue() {
        return ordinal() + 1;
    }
}
