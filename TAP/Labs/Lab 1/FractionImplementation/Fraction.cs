using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Laboratorio1.FractionImplementation {
    internal class Utilities {
        // Function to get the greatest common divisor between two numbers
        public static int gcd(int a, int b) {
            if (a == b)
                return a;
            if (a > b)
                return gcd(a - b, b);
            return gcd(a, b - a);
        }
        // Function to get the least common multiple between two numbers
        public static int lcm(int a, int b) {
            return (a * b) / gcd(a, b);
        }
    }

    internal class Fraction {
        public int Numerator { get;  }
        public int Denominator { get; }

        private void Normalize(ref int num, ref int den) {
            var gcd = Utilities.gcd(num, den);
            while (gcd != 1) {
                num /= gcd;
                den /= gcd;
                gcd = Utilities.gcd(num, den);
            }
        }

        
        private static bool CheckNegative(int num, int den) {
            return (num < 0 ^ den < 0);
        }

        public Fraction(int numerator, int denominator) {
            if (denominator == 0)
                throw new ArgumentException("Denominator = 0 found");
            if (numerator == 0) {
                Numerator = numerator;
                Denominator = 1; // if the fraction is 0, set the denominator to 1 to avoid any weird behaviour
                return;
            }
            var negative = CheckNegative(numerator, denominator); // use a bool to save the sign of the fraction and change it after the normalization that happens with both values positive
            numerator = Math.Abs(numerator);
            denominator = Math.Abs(denominator);
            Normalize(ref numerator, ref denominator);
            if (negative)
                numerator = -numerator;
            Numerator = numerator;
            Denominator = denominator;
        }

        public static Fraction operator +(Fraction a, Fraction b) {
            var den = Utilities.lcm(a.Denominator, b.Denominator);
            var firstNum = a.Numerator * (den / a.Denominator);
            var secondNum = b.Numerator * (den / b.Denominator);
            return new Fraction(firstNum + secondNum, den); // Normalization happens in the constructor
        }

        public static Fraction operator -(Fraction a, Fraction b) {
            var opposite_b = new Fraction(-b.Numerator, b.Denominator);
            return a + opposite_b;
        }

        public static Fraction operator *(Fraction a, Fraction b) {
            return new Fraction(a.Numerator * b.Numerator, a.Denominator * b.Denominator); // Normalization and multiplication by 0 happens in the constructor
        }

        public static Fraction operator /(Fraction a, Fraction b) {
            if (b.Numerator == 0)
                throw new DivideByZeroException();
            var reciprocal_b = new Fraction(b.Denominator, b.Numerator);
            return a * reciprocal_b;
        }

        public override string ToString() {
            if (Denominator != 1)
                return Numerator + "/" + Denominator;
            else
                return Numerator.ToString();
        }

        public override bool Equals(object? other) {
            if (this == other)
                return true;
            if (other == null)
                return false;
            if(other is Fraction fraction)
                return (Numerator == fraction.Numerator && Denominator == fraction.Denominator);
            return false;
        }

        public override int GetHashCode() {
            return (Numerator * Denominator).GetHashCode(); // Multiply so that if the denominator is 1 it treats the fraction like an int
        }

        public static implicit operator Fraction(int num) {
            return new Fraction(num, 1);
        }

        public static explicit operator int(Fraction from) {
            if (from.Denominator != 1)
                throw new ArgumentException("Tried converting fraction with denominator != 1 to int");
            return from.Numerator;
        }
    }
}
