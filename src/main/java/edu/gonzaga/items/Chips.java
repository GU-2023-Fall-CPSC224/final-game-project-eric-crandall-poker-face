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

    public Integer[] getNumChips() {
        return this.numChips;
    }
    public Integer getChipsAt(Integer i) {
        return 0;
    }

    public void setChipsAt(Integer i, Integer newChips) {

    }

    public void updateChips(Integer x, Integer y, Integer z) {

    }

    public Integer getTotalChipsValues() {

        return 0;
    }
}
