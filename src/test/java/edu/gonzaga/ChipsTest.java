package edu.gonzaga;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import edu.gonzaga.items.Chips;

public class ChipsTest {
    @Test
    void testChipDVCandGetter() {
        Chips chips = new Chips();
        
        Integer actual = chips.getChipsAt(0);
        Integer expected = 15;
        assertEquals(actual, expected);
    }

    @Test
    void testSetChipsAt() {
        Chips chips = new Chips();
        chips.setChipsAt(0, 30);

        Integer actual = chips.getChipsAt(0);
        Integer expected = 30;
        assertEquals(actual, expected);
    }

    @Test
    void testUpdateChips() {
        Chips chips = new Chips();
        chips.updateChips(10, -5, 1);

        Integer actual[] = chips.getNumChips();
        Integer expected[] = {25, 5, 6};
        assertEquals(actual, expected);
    }

    @Test
    void testGetTotalChipsValue() {
        Chips chips = new Chips();

        Integer actual = chips.getTotalChipsValues();
        Integer expected = 115;
        assertEquals(actual, expected);
    }
}
