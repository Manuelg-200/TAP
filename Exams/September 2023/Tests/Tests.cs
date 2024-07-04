using CardGame;
using Moq;

namespace Tests {
    public class Tests {
        private static readonly object[] TestCases = {
            new object[] {
                new Cards[] { Cards.Ace, Cards.Two, Cards.Three },
                new Suits[] { Suits.Hearts, Suits.Diamonds, Suits.Clubs }
            },
            new object[] {
                new Cards[] { Cards.Seven, Cards.Jack, Cards.King },
                new Suits[] { Suits.Spades, Suits.Hearts, Suits.Diamonds }
            },
            new object[] {
                new Cards[] { Cards.Seven, Cards.Jack, Cards.Queen },
                new Suits[] { Suits.Clubs, Suits.Spades, Suits.Diamonds }
            }
        };

        [TestCaseSource(nameof(TestCases))]
        public void Test1(Cards[] cards, Suits[] suits) {
            if(cards.Length != suits.Length)
                Assert.Inconclusive("Test1 is inconclusive: different lengths");
            if(cards.Length % 3 != 0 || cards.Length % 3 != 0)
                Assert.Inconclusive("Test1 is inconclusive: length not a multiple of 3");
            var deck = new List<IPlayingCard>();
            for (var i = 0; i < cards.Length; i++) {
                var mock = new Mock<IPlayingCard>();
                mock.SetupGet(x => x.Value).Returns(cards[i]);
                mock.SetupGet(x => x.Suit).Returns(suits[i]);
                deck.Add(mock.Object);
                deck.Add(mock.Object);
            }

            var result = deck.FirstWins().ToList();
            Assert.That(result, Is.All.True);
        }

        [Test]
        public void Test2() {
            var randomGenerator = new Random();
            var deck = Deck(randomGenerator);
            var result = deck.FirstWins().Take(1000).ToList();
            Assert.That(result.Count, Is.GreaterThanOrEqualTo(1000));
            return;

            static IEnumerable<IPlayingCard> Deck(Random generator) {
                while (true) {
                    var mock = new Mock<IPlayingCard>();
                    mock.SetupGet(x => x.Value).Returns((Cards)generator.Next(9));
                    mock.SetupGet(x => x.Suit).Returns((Suits)generator.Next(4));
                    yield return mock.Object;
                }
            }
        }

        [Test]
        public void Test3() {
            var deck = new List<IPlayingCard>();
            for (var i = 0; i < 7; i++) {
                var mock = new Mock<IPlayingCard>();
                deck.Add(mock.Object);
            }
            Assert.That(() => deck.FirstWins().ToList(), Throws.ArgumentException);
        }
    }
}