using InfiniteComparisonSequenceExtension;
using Moq;

namespace Tests {
    class Tests
    {
        class Fake
        {
            public int CompareTo(Fake? other)
            {
                throw new NotImplementedException();
            }
        }

        /// <summary>
        /// This sanity check only verifies
        /// </summary>
        [Test]
        public void SanityCheck()
        {
            var x1 = new[] { 1, 2, 3 }.InfiniteComparisonSequence();
            var x2 = new List<bool>() { true, false }.InfiniteComparisonSequence();
            // Togliendo il commento alla dichiarazione di x4 il test non dovrebbe più compilare
            // var x4 = new List<Fake>() { new Fake(), new Fake(), new Fake() }.InfiniteComparisonSequence();
        }

        [Test]
        public void Test1()
        {
            var source = new List<char>();
            var result = source.InfiniteComparisonSequence().ToList();
            Assert.That(result, Has.Count.EqualTo(0));
        }

        [Test]
        public void Test2()
        {
            var source = new List<bool>() { true, false, true, true, false };
            Assert.That(() => source.InfiniteComparisonSequence().ToList(), Throws.ArgumentException);
        }
        
        [Test]
        public void Test3()
        {
            var source = Sequence().Take(20).ToList();
            source.InfiniteComparisonSequence().Take(10).ToList();
            foreach (var e in source)
            {
                Mock.Verify();
            }
            return;

            static IEnumerable<int> Sequence()
            {
                var i = 0;
                while (true)
                {
                    var mock = new Mock<IComparable<int>>();
                    mock.Setup(x => x).Returns(i++);
                    yield return (int)mock.Object;
                }
            }
        }

        [TestCase(new[] {1, 5, 5, 3, -10, 7 }, new[] { -1, 0, 1, 1, -1 })]
        public void Test4(int[] theSource, int[] expected)
        {
            if (theSource.Length != expected.Length + 1)
                Assert.Inconclusive("Source and expected arrays have wrong length");
            var size = expected.Length;
            var result = theSource.InfiniteComparisonSequence().Take(size);
            Assert.Multiple(() =>
            {
                var expectedIndex = 0;
                foreach (var e in result)
                {
                    Assert.That(e, Is.EqualTo(expected[expectedIndex]));
                    expectedIndex++;
                }
            });
        }

        
        [TestCase(new[]{ 1, 5, 5, 3, -10, 7 }, new[] { -1, 0, 1, 1, -1 })]
        [TestCase(new[]{"qui", "quo", "qua"}, new[]{-1, 1})]
        [TestCase(new[]{true, false, false, true}, new[]{1, 0, -1})]
        public void Test5<T>(T theSource, int[] expected) where T : IComparable<T>
        {
            var sourceSequence = theSource as IEnumerable<T>;
            var source = sourceSequence.ToList();
            if(source.Count != expected.Length+1)
                Assert.Inconclusive("Source and expected arrays have wrong length");
            var size = expected.Length;
            var result = source.InfiniteComparisonSequence().Take(size).ToList();
            Assert.Multiple(() =>
            {
                var expectedIndex = 0;
                foreach (var e in result)
                {
                    Assert.That(e, Is.EqualTo(expected[expectedIndex]));
                    expectedIndex++;
                }
            });
        }
    }
}