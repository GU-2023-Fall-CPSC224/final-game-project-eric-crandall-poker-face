package edu.gonzaga;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.gonzaga.items.Scorer;
import edu.gonzaga.items.Card;
import edu.gonzaga.items.FaceValue;
import edu.gonzaga.items.Suit;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ScorerTest {
    
    @Test
    void sortHandTest() {
        Scorer scorer = new Scorer();
        Card temp = new Card();

        //Straigh 4-8 and all spades
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.FOUR);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.SIX);
        scorer.addCardtoHand(temp); 
        temp = new Card();
        temp.setFaceValue(FaceValue.FIVE);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.SEVEN);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.EIGHT);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.EIGHT);
        scorer.addCardtoHand(temp); 

        Integer expected[] = {4, 5, 6, 7, 8, 8, 14};
        scorer.sortHand();
        Integer actual[] = {0, 0, 0, 0, 0, 0 ,0};
        for(int i = 0; i < 7; i++) {
            actual[i] = scorer.getHandAt(i).getFaceValue().getValue();
        }
        assertEquals(expected[0], actual[0]);
    }

    @Test
    void checkHighCardTest() {
        Scorer scorer = new Scorer();
        Card temp = new Card();

        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.FIVE);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.JACK);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.KING);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.FOUR);
        scorer.addCardtoHand(temp);

        scorer.checkHighCard();
        FaceValue expected = FaceValue.ACE;
        FaceValue actual = scorer.getHighCardVal();
        assertEquals(expected, actual);
    }

    @Test
    void check1PairTest() {
        Scorer scorer = new Scorer();
        Card temp = new Card();

        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.FIVE);
        scorer.addCardtoHand(temp);
        scorer.addCardtoHand(temp); //Should have 2 fives
        temp = new Card();
        temp.setFaceValue(FaceValue.KING);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.FOUR);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.THREE);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.EIGHT);
        scorer.addCardtoHand(temp);

        scorer.countDupes();
        boolean expected = true;
        boolean actual = scorer.check1Pair();
        assertEquals(expected, actual);
    }

    @Test
    void check2PairTest() {
        Scorer scorer = new Scorer();
        Card temp = new Card();

        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.FIVE);
        scorer.addCardtoHand(temp);
        scorer.addCardtoHand(temp); //Should have 2 fives
        temp = new Card();
        temp.setFaceValue(FaceValue.KING);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.FOUR);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.THREE);
        scorer.addCardtoHand(temp);
        scorer.addCardtoHand(temp); //And 2 threes

        scorer.countDupes();
        boolean expected = true;
        boolean actual = scorer.check2Pair();
        assertEquals(expected, actual);
    }

    @Test
    void check3KindTest() {
        Scorer scorer = new Scorer();
        Card temp = new Card();

        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.SIX);
        scorer.addCardtoHand(temp);
        scorer.addCardtoHand(temp); //Should have 3 sixes
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.FOUR);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.THREE);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.EIGHT);
        scorer.addCardtoHand(temp);

        scorer.countDupes();
        boolean expected = true;
        boolean actual = scorer.check3Kind();
        assertEquals(expected, actual);
    }


    @Test
    void checkStraightTest() {
        Scorer scorer = new Scorer();
        Card temp = new Card();

        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.SIX);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.FIVE);
        scorer.addCardtoHand(temp); 
        temp = new Card();
        temp.setFaceValue(FaceValue.FOUR);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.THREE);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.TWO);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.EIGHT);
        scorer.addCardtoHand(temp);

        scorer.countDupes();
        boolean expected = true;
        boolean actual = scorer.checkStraight();
        assertEquals(expected, actual);
    }

    @Test
    void checkFlushTest() {
        Scorer scorer = new Scorer();
        Card temp = new Card();

        //Should have 5 spades and 2 hearts
        scorer.addCardtoHand(temp);
        scorer.addCardtoHand(temp);
        scorer.addCardtoHand(temp);
        scorer.addCardtoHand(temp);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setSuit(Suit.HEARTS);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setSuit(Suit.HEARTS);
        scorer.addCardtoHand(temp);

        boolean expected = true;
        boolean actual = scorer.checkFlush();
        assertEquals(expected, actual);
    }

    @Test
    void checkFullHouseTest() {
        Scorer scorer = new Scorer();
        Card temp = new Card();

        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.SIX);
        scorer.addCardtoHand(temp);
        scorer.addCardtoHand(temp); //2 Sixes
        temp = new Card();
        temp.setFaceValue(FaceValue.FOUR);
        scorer.addCardtoHand(temp);
        scorer.addCardtoHand(temp);
        scorer.addCardtoHand(temp); //3 fours
        temp = new Card();
        temp.setFaceValue(FaceValue.EIGHT);
        scorer.addCardtoHand(temp);

        scorer.countDupes();
        boolean expected = true;
        boolean actual = scorer.checkFullHouse();
        assertEquals(expected, actual);
    }

    @Test
    void check4KindTest() {
        Scorer scorer = new Scorer();
        Card temp = new Card();

        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.SIX);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.FIVE);
        scorer.addCardtoHand(temp); 
        temp = new Card();
        temp.setFaceValue(FaceValue.FOUR);
        scorer.addCardtoHand(temp);
        scorer.addCardtoHand(temp);
        scorer.addCardtoHand(temp);
        scorer.addCardtoHand(temp); //4 fours

        scorer.countDupes();
        boolean expected = true;
        boolean actual = scorer.check4Kind();
        assertEquals(expected, actual);
    }

    @Test
    void checkStraightFlushTest() {
        Scorer scorer = new Scorer();
        Card temp = new Card();

        //Straigh 4-8 and all spades
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.FOUR);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.SIX);
        scorer.addCardtoHand(temp); 
        temp = new Card();
        temp.setFaceValue(FaceValue.FIVE);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.SEVEN);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.EIGHT);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.EIGHT);
        scorer.addCardtoHand(temp);

        scorer.countDupes();
        boolean expected = true;
        boolean actual = scorer.checkStraightFlush();
        assertEquals(expected, actual);
    }

    @Test
    void checkRoyalFlushTest() {
        Scorer scorer = new Scorer();
        Card temp = new Card();
        
        //10-A all of same suit
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.KING);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.QUEEN);
        scorer.addCardtoHand(temp); 
        temp = new Card();
        temp.setFaceValue(FaceValue.JACK);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.TEN);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.TWO);
        scorer.addCardtoHand(temp);
        temp = new Card();
        temp.setFaceValue(FaceValue.EIGHT);
        scorer.addCardtoHand(temp);

        boolean expected = true;
        boolean actual = scorer.checkRoyalFlush();
        assertEquals(expected, actual);
    }
}
