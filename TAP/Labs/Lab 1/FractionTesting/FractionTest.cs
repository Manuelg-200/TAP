using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection.Metadata.Ecma335;
using System.Security.Cryptography.X509Certificates;
using System.Text;
using System.Threading.Tasks;
using Laboratorio1.FractionImplementation;

namespace Laboratorio1.FractionTesting {
    [TestFixture]
    public class ConstructorTests {

        [TestCase(5,7)]
        [TestCase(-5, 7)]
        [TestCase(5, -7)]
        public void BasicConstructorTest(int num, int den) {
            var res = new Fraction(num, den); 
            Assert.Multiple(() => {
                Assert.That(res.Numerator, Is.EqualTo(num));
                Assert.That(res.Denominator, Is.EqualTo(den));
            });
        }

        [Test]
        public void NegativeDenominatorConstructorTest() {
            var res = new Fraction(5, -7);
            Assert.Multiple(() => {
                Assert.That(res.Numerator, Is.EqualTo(-5));
                Assert.That(res.Denominator, Is.EqualTo(7));
            });
        }

        [TestCase(32, 40)]
        [TestCase(-32, -40)]
        public void NotCoprimesPositiveConstructorTest(int num, int den) {
            var res = new Fraction(num, den);
            Assert.Multiple(() => {
                Assert.That(res.Numerator, Is.EqualTo(4));
                Assert.That(res.Denominator, Is.EqualTo(5));
            });
        }

        [TestCase(-32, 40)]
        [TestCase(32, -40)]
        public void NotCoprimesNegativeConstructorTest(int num, int den) {
            var res = new Fraction(num, den);
            Assert.Multiple(() => {
                Assert.That(res.Numerator, Is.EqualTo(-4));
                Assert.That(res.Denominator, Is.EqualTo(5));
            });
        }

        [Test]
        public void ZeroDenominatorConstructorTest() {
            Assert.That(() => new Fraction(5, 0), Throws.ArgumentException);
        }
    }

    [TestFixture]
    public class OperatorsTests {
        [Test]
        public void SumTest() {
            var a = new Fraction(3, 2);
            var b = new Fraction(2, 3);
            var sum = a + b;
            Assert.Multiple(() => {
                Assert.That(sum.Numerator, Is.EqualTo(13));
                Assert.That(sum.Denominator, Is.EqualTo(6));
            });
        }

        [Test]
        public void SubtractionTest() {
            var a = new Fraction(3, 2);
            var b = new Fraction(2, 3);
            var sub = a - b;
            Assert.Multiple(() => {
                Assert.That(sub.Numerator, Is.EqualTo(5));
                Assert.That(sub.Denominator, Is.EqualTo(6));
            });
        }

        [Test]
        public void ProductTest() {
            var a = new Fraction(3, 2);
            var b = new Fraction(1, 15);
            var prod = a * b;
            Assert.Multiple(() => {
                Assert.That(prod.Numerator, Is.EqualTo(1));
                Assert.That(prod.Denominator, Is.EqualTo(10));
            });
        }

        [Test]
        public void ProductByZeroTest() {
            var a = new Fraction(3, 2);
            var b = new Fraction(0, 3); // denominator value doesn't matter in this case
            var prod = a * b;
            Assert.Multiple(() => {
                Assert.That(prod.Numerator, Is.EqualTo(0));
                Assert.That(prod.Denominator, Is.EqualTo(1));
            });
        }
        [Test]
        public void DivisionTest() {
            var a = new Fraction(3, 2);
            var b = new Fraction(2, 10);
            var div = a / b;
            Assert.Multiple(() => {
                Assert.That(div.Numerator, Is.EqualTo(15));
                Assert.That(div.Denominator, Is.EqualTo(2));
            });
        }

        [Test]
        public void DivisionByZeroTest() {
            var a = new Fraction(3, 2);
            var b = new Fraction(0, 1); // denominator value doesn't matter in this case
            Assert.That(() => a/b, Throws.InstanceOf<DivideByZeroException>());
        }
    }

    [TestFixture]
    public class OtherTests {
        
        [Test]
        public void ToStringFractionTest() {
            var res = new Fraction(3, 2);
            Assert.That(res.ToString(), Is.EqualTo("3/2"));
        }

        [Test]
        public void ToStringNormalizedFractionTest() {
            var res = new Fraction(22, 11);
            Assert.That(res.ToString(), Is.EqualTo("2"));
        }

        [Test]
        public void ToStringNormalizedNegativeFractionTest() {
            var res = new Fraction(22, -11);
            Assert.That(res.ToString(), Is.EqualTo("-2"));
        }

        [Test]
        public void EqualsFractionTest() {
            var a = new Fraction(3, 2);
            var b = new Fraction(6, 4);
            Assert.That(a, Is.EqualTo(b));
        }

        [Test]
        public void EqualsFractionZeroTest() {
            var a = new Fraction(0, 200);
            var b = new Fraction(0, 5);
            Assert.That(a, Is.EqualTo(b));
        }

        [Test]
        public void IntToFractionImplicitTest() {
            Fraction res = 3;
            Assert.Multiple(() => {
                Assert.That(res.Numerator, Is.EqualTo(3));
                Assert.That(res.Denominator, Is.EqualTo(1));
            });
        }

        [Test]
        public void ZeroToFractionImplicitTest() {
            Fraction res = 0;
            Assert.Multiple(() => {
                Assert.That(res.Numerator, Is.EqualTo(0));
                Assert.That(res.Denominator, Is.EqualTo(1));
            });
        }

        [Test]
        public void FractionToIntExplicitSuccessTest() {
            var res = new Fraction(3, 1);
            var num = (int)res;
            Assert.That(num, Is.EqualTo(3));
        }

        [Test]
        public void FractionToIntExplicitFailureTest() {
            var res = new Fraction(3, 2);
            Assert.That(() => (int)res, Throws.ArgumentException);
        }

    }
}
