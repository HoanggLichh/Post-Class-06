package ch5;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

public class TestPokerHand {

    // ===== Helper methods =====
    private static Card card(Rank pRank, Suit pSuit) {
        return new Card(pRank, pSuit);
    }

    private static PokerHand hand(Card... pCards) {
        return new PokerHand(pCards);
    }

    /** Calls the private method isStraightFlush() through reflection. */
    private static boolean isStraightFlush(PokerHand pHand) {
        try {
            Method method = PokerHand.class.getDeclaredMethod("isStraightFlush");
            method.setAccessible(true);
            return (boolean) method.invoke(pHand);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail(e);
            return false;
        }
    }

    // ===== Exercise 5.8: one test case whose output is true =====
    @Test
    void testStraightFlush() {
        PokerHand h = hand(card(Rank.TWO, Suit.HEARTS), card(Rank.THREE, Suit.HEARTS),
                card(Rank.FOUR, Suit.HEARTS), card(Rank.FIVE, Suit.HEARTS),
                card(Rank.SIX, Suit.HEARTS));
        assertTrue(isStraightFlush(h));
    }

    // ===== Exercise 5.8: all possible straight flushes = 9 x 4 = 36 executions =====
    @Test
    void testAllStraightFlushes() {
        Rank[] ranks = Rank.values();
        int count = 0;
        for (Suit suit : Suit.values()) {
            for (int start = 0; start + 4 < ranks.length; start++) {
                PokerHand h = hand(card(ranks[start], suit), card(ranks[start + 1], suit),
                        card(ranks[start + 2], suit), card(ranks[start + 3], suit),
                        card(ranks[start + 4], suit));
                assertTrue(isStraightFlush(h));
                count++;
            }
        }
        assertEquals(36, count);
    }

    // ===== Exercise 5.9 / 5.10: covers the "return false" statement and the if-true branch =====
    @Test
    void testNotStraightFlush_differentSuit() {
        PokerHand h = hand(card(Rank.TWO, Suit.HEARTS), card(Rank.THREE, Suit.SPADES),
                card(Rank.FOUR, Suit.HEARTS), card(Rank.FIVE, Suit.HEARTS),
                card(Rank.SIX, Suit.HEARTS));
        assertFalse(isStraightFlush(h));
    }

    // ===== Extra (condition coverage): same suit, ranks not consecutive =====
    @Test
    void testNotStraightFlush_nonConsecutiveRank() {
        PokerHand h = hand(card(Rank.TWO, Suit.HEARTS), card(Rank.THREE, Suit.HEARTS),
                card(Rank.FOUR, Suit.HEARTS), card(Rank.FIVE, Suit.HEARTS),
                card(Rank.SEVEN, Suit.HEARTS));
        assertFalse(isStraightFlush(h));
    }

    // ===== Extra: early exit at every position (guards against off-by-one in the loop) =====
    @Test
    void testNotStraightFlush_mismatchAtEachPosition() {
        Rank[] ranks = {Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE, Rank.SIX};
        for (int bad = 0; bad < ranks.length; bad++) {
            Card[] cards = new Card[ranks.length];
            for (int i = 0; i < ranks.length; i++) {
                cards[i] = card(ranks[i], i == bad ? Suit.SPADES : Suit.HEARTS);
            }
            assertFalse(isStraightFlush(hand(cards)), "bad suit at position " + bad);
        }
    }

    // ===== Extra: constructor sorts, so the order of the arguments must not matter =====
    @Test
    void testStraightFlush_inputOrderDoesNotMatter() {
        PokerHand h = hand(card(Rank.SIX, Suit.CLUBS), card(Rank.FIVE, Suit.CLUBS),
                card(Rank.FOUR, Suit.CLUBS), card(Rank.THREE, Suit.CLUBS),
                card(Rank.TWO, Suit.CLUBS));
        assertTrue(isStraightFlush(h));
    }

    // ===== Extra (characterization test): ACE is only high, so A-2-3-4-5 is NOT recognised =====
    @Test
    void testAceLowIsNotRecognised() {
        PokerHand h = hand(card(Rank.ACE, Suit.DIAMONDS), card(Rank.TWO, Suit.DIAMONDS),
                card(Rank.THREE, Suit.DIAMONDS), card(Rank.FOUR, Suit.DIAMONDS),
                card(Rank.FIVE, Suit.DIAMONDS));
        assertFalse(isStraightFlush(h));
    }
}
