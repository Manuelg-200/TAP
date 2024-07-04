namespace CardGame {
    public static class FirstWinsExtension {
        private static IPlayingCard FindHighest(List<IPlayingCard> hand) {
            var highest = hand[0];
            for (var i = 1; i < 3; i++) {
                if (highest <= hand[i])
                    highest = hand[i];
            }

            return highest;
        }

        public static IEnumerable<bool> FirstWins(this IEnumerable<IPlayingCard> s) {
            var firstPlayer = true;
            var firstPlayerHand = new List<IPlayingCard>();
            var secondPlayerHand = new List<IPlayingCard>();
            using var currentCard = s.GetEnumerator();
            while (currentCard.MoveNext()) {
                if (firstPlayer)
                    firstPlayerHand.Add(currentCard.Current);
                else
                    secondPlayerHand.Add(currentCard.Current);
                // Invert the boolean for next card
                firstPlayer = !firstPlayer;
                // The hand of the game is three cards
                if (firstPlayerHand.Count == 3 && secondPlayerHand.Count == 3) {
                    yield return (FindHighest(firstPlayerHand) >= FindHighest(secondPlayerHand));
                    firstPlayerHand.Clear();
                    secondPlayerHand.Clear();
                }
            } 
            // The deck s has not enough cards to play
            if (firstPlayerHand.Count > 0 || secondPlayerHand.Count > 0)
                throw new ArgumentException("There are not enough cards!");
        }
    }
}