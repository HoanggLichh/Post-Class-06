package ch5;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class PokerHand {

    private final List<Card> aCards;

    public PokerHand(Card... pCards) {
        assert pCards.length == 5;
        aCards = Arrays.asList(pCards);
        aCards.sort(new Comparator<Card>() {
            public int compare(Card pCard1, Card pCard2) {
                return pCard1.getRank().compareTo(pCard2.getRank());
            }
        });
    }

    private boolean isStraightFlush() {
        Suit suit = aCards.get(0).getSuit();
        Rank rank = aCards.get(0).getRank();
        Iterator<Card> iterator = aCards.iterator();
        iterator.next();
        while (iterator.hasNext()) {
            Card card = iterator.next();
            if (card.getSuit() != suit || card.getRank().ordinal() - rank.ordinal() != 1) {
                return false;
            }
            rank = card.getRank();
        }
        return true;
    }
}
