using EnoughSmallerExtension;
using Moq;

namespace Tests {
    public class Tests {
        [Test]
        public void Test1() {
            var s = new List<char> { 'c', 'i', 'a', 'o' };
            Assert.That(() => s.EnoughSmaller('y', 0), Throws.TypeOf<ArgumentOutOfRangeException>());
        }

        [Test]
        public void Test2() {
            var s = new List<string> { "It's over", "Anakin!", "I have", "the high ground", "!" };
            var threshold = "You underestimate my power!";
            Assert.That(s.EnoughSmaller(threshold, 42), Is.False);
        }

        [TestCase(20)]
        [TestCase(1)]
        [TestCase(1000)]
        public void Test3(int n) {
            if(n <= 0)
                Assert.Inconclusive("Test3 needs the n parameter to be positive");
            var s = Sequence(new Random());
            Assert.That(s.EnoughSmaller(7.42, n), Is.True);
            return;

            static IEnumerable<double> Sequence(Random generator) {
                while (true)
                    yield return -generator.Next(100);
            }
        }

        [Test]
        public void Test4() {
            var mock = new Mock<IComparable<int>>();
            mock.Setup(x => x.CompareTo(It.IsAny<int>())).Returns(1);

            var s = new List<int>();
            for (var i = 0; i < 20; i++)
                s.Add(mock.Object);
            s.EnoughSmaller(21, 7);
            mock.Verify(x => x.CompareTo(It.IsAny<int>()), Times.Exactly(7));
        }
    }
}