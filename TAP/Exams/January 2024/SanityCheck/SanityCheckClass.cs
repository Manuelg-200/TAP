using ExtensionClass;
using Moq;

namespace SanityCheck {
    public class SanityCheckClass {
        [Test]
        public void SanityCheck() {
            Func<int, int> ff = x => x;
            var source = new[] { ff };
            var result = source.IntersectOn(source, 0);
            Assert.That(result.Count(), Is.EqualTo(1));
        }
    }

    [TestFixture]
    public class Tests {
        [Test]
        public void Test1() {
            var source = new List<Func<int, bool>>{
                x => true,
                x => (x%2 == 0),
                x => false
            };
            var other = new List<Func<int, bool>> {
                x => (x % 3 == 0),
                x => (x < 10),
                x => (x % 5 == 0)
            };
            var result = source.IntersectOn(other, 6).ToList();
            Assert.That(result.All(r => true));
        }

        [Test]
        public void Test2() {
            var source = Enumerable.Range(1, 20).Select(i => {
                var mock = new Mock<Func<int, int>>();
                mock.Setup(f => f(It.IsAny<int>())).Returns(i);
                return mock;
            }).ToList();

            var other = Enumerable.Range(1, 20).Select(i => {
                var mock = new Mock<Func<int, int>>();
                mock.Setup(f => f(It.IsAny<int>())).Returns<int>(x => x * i);
                return mock;
            }).ToList();

            var result = source.Select(m => m.Object).IntersectOn(other.Select(m => m.Object), 42).ToList();

            foreach(var mock in source.Concat(other))
                mock.Verify(f => f(42), Times.Exactly(1));
        }

        [Test]
        public void Test3() {
            var source = new List<Func<int, int>> { x => x };
            var other = OtherFunction();
            Assert.That(() => source.IntersectOn(other, 0).ToList(), Throws.ArgumentException );
            return;

            static IEnumerable<Func<int, int>> OtherFunction() {
                while(true)
                    yield return x => x;
            }
        }

        [Test]
        public void Test4() {
            var source = new List<Func<int, bool>> {
                x => true,
                x => {
                    if (x % 2 == 0)
                        throw new ArgumentException();
                    return true;
                },
                x => (x<7)
            };
            var other = new List<Func<int, bool>> {
                x => false,
                x => (x<10),
                x => (x%5 == 0)
            };
            var result = source.IntersectOn(other, 666).ToList();
            Assert.Multiple(() => {
                Assert.That(result[0], Is.False);
                Assert.That(result[1], Is.Null);
                Assert.That(result[2], Is.True);
            });
        }
    }
}