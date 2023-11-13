/** Class Name: Chips
 *  Desc: Chips contains an array(arrayList?) that stores how many chips of each kind an indiviudal has.
 *        Methods are mainly used to get, set, and update chips
 *  Notes: }:)
 */

 package edu.gonzaga.items;

public class Chips {
    /* Values of chips based on index
     * Index: 0 -- Value: $1
     * Index: 1 -- Value: $5
     * Index: 2 -- Value: $10
     */
    private Integer numChips[]; 
    
    private static final Integer DEFAULT_CHIP_VALUES[] = {1, 5, 10};
    private static final Integer DEFAULT_NUM_CHIP_TYPES = 3;

    /* Default Value Constructor for chips
     * Creates an array size 3 of chips with default values of 15, 10, 5
     */
    public Chips() {
        Integer[] temp = {15, 10, 5};
        this.numChips = temp;
    }

    /* Method Name: getNumChips()
     * Returns: An Integer Array
     * Desc: Returns chips numChips array
     * Events:
     */
    public Integer[] getNumChips() {
        return this.numChips;
    }

    /* Method Name: getChipsAt()
     * Returns: An Integer
     * Desc: Returns the integer value of numChips at a given index i
     * Events:
     */
    public Integer getChipsAt(Integer i) {
        if(0 <= i && i < DEFAULT_NUM_CHIP_TYPES) {
            return numChips[i];
        }
        return -1;
    }

    /* Method Name: setChipsAt
     * Returns: N/A (Void)
     * Desc: Accepts an idex i and an int newChips. Sets the numChips at i to newChips value
     * Events:
     */
    public void setChipsAt(Integer i, Integer newChips) {
        numChips[i] = newChips;
    }

    /* Method Name: updateChips()
     * Returns: N/A (Void)
     * Desc: Accepts Integers x, y, z and adds those values to numChips at 0, 1, 2 (Respectively)
     * Events:
     */
    public void updateChips(Integer x, Integer y, Integer z) {
        this.numChips[0] += x;
        this.numChips[1] += y;
        this.numChips[2] += z;
    }

    /* Method Name: getTotalChipsValues()
     * Returns: an Integer
     * Desc: Multiplys the num each type of chip by its default value and adds them together. Returns the result
     * Events:
     */
    public Integer getTotalChipsValues() {
        return (numChips[0] * DEFAULT_CHIP_VALUES[0]) + (numChips[1] * DEFAULT_CHIP_VALUES[1]) + (numChips[2] * DEFAULT_CHIP_VALUES[2]);
    }
}
