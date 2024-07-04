namespace CardGame {
    public enum Cards { Ace, Two, Three, Four, Five, Six, Seven, Jack, Queen, King }
    public enum Suits { Spades, Clubs, Diamonds, Hearts }

    public interface IPlayingCard {
        Cards Value { get; }
        Suits Suit { get; }
        static bool operator <=(IPlayingCard first, IPlayingCard second) {
            if (first.Value == second.Value)
                return (first.Suit <= second.Suit);
            return (first.Value < second.Value);
        }
        static bool operator >=(IPlayingCard first, IPlayingCard second) { 
            if (first.Value == second.Value) 
                return (first.Suit >= second.Suit);
            return (first.Value > second.Value);
        }
    }
}