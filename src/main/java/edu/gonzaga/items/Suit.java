/** Enum Name: Suit
 *  Desc: Creates a list of Enumerated Values for Suits (Spades, Clubs, Hearts, Diamonds)
 *        Methods are used to get get the string value of a suit
 *  Notes: Documented by McEwan Bain
 */
package edu.gonzaga.items;

public enum Suit {
    SPADES("Spades"),
    CLUBS("Clubs"),
    HEARTS("Hearts"),
    DIAMONDS("Diamonds");

    private final String value;

    /* Constructor for Suit
     * Accepts a string and sets Suits value to that string
     */
    Suit(String value) {
        this.value = value;
    }

    /* Method Name: toString()
     * Returns: A String
     * Desc: Returns the string value tied to a Suit
     */
    @Override
    public String toString() {
        return this.value;
    }
}
