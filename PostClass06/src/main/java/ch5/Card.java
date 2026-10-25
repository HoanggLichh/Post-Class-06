package ch5;

public class Card {
    private final Rank aRank;
    private final Suit aSuit;

    public Card(Rank pRank, Suit pSuit) {
        aRank = pRank;
        aSuit = pSuit;
    }

    public Rank getRank() { return aRank; }
    public Suit getSuit() { return aSuit; }
}
