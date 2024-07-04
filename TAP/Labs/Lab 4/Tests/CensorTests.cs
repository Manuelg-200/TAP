using System.Collections;
using Moq;
using IMessage;
using Censor;
using NUnit.Framework.Constraints;

namespace Tests {
    [TestFixture]
    public class CensorTests {
        [Test]
        public void BasicCensorTest() {
            var mock1 = new Mock<I>();
            mock1.Setup(m => m.Message).Returns("Hello there");
            var mock2 = new Mock<I>();
            mock2.Setup(m => m.Message).Returns("Hello world");
            IEnumerable<I> sequence = new List<I> { mock1.Object, mock2.Object };
            var result = Censorship.Censor(sequence, "there");
            var toCompare = new List<I> { mock2.Object };
            Assert.That(result, Is.EqualTo(toCompare));
        }

        [TestCase(20)]
        public void InfiniteSequenceTest(int elementNumber) {
            var toCompare = new Mock<I>[elementNumber];
            for (int i = 0; i < elementNumber; i++) {
                toCompare[i] = new Mock<I>();
                toCompare[i].Setup(m => m.Message).Returns("aaaa");
            }
            IEnumerable<I> Sequence() {
                for (int i = 0; i < elementNumber; i++)
                    yield return toCompare[i].Object;
                while (true) {
                    var mock = new Mock<I>();
                    mock.Setup(m => m.Message).Returns("aaaa");
                    yield return mock.Object;
                }
            }
            var result = Censorship.Censor(Sequence(), "bbbb").Take(elementNumber);
            Assert.That(result, Is.EqualTo(toCompare.Select(a => a.Object)));
        }
        [Test]
        public void NullSequenceTest() {

            Assert.That(() => Censorship.Censor(null, "a").Any(), Throws.ArgumentNullException);
        }
    }
}