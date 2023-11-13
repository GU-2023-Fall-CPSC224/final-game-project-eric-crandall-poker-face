/** Enum Name: Suit
 *  Desc: Creates a list of Enumerated Values for Suits (Spades, Clubs, Hearts, Diamonds)
 *        Methods are used to get get the string value of a suit
 *  Notes: -_-
 */
package edu.gonzaga.items;

public enum Suit {
    SPADES("Spade"),
    CLUBS("Club"),
    HEARTS("Heart"),
    DIAMONDS("Diamond");

    private final String value;

    /* Contrcutor for Suit
     * Accepts a string and sets Suits value to that string
     */
    Suit(String value) {
        this.value = value;
    }

    /* Method Name: getString()
     * Returns: A String
     * Desc: Returns the string value tied to a Suit
     */
    public String getString() {
        return this.value;
    }
}
