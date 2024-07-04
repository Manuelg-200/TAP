using System.Runtime.InteropServices;
using System.Security.Cryptography.X509Certificates;
using MyAttribute;

namespace MyLibrary {
    public class Foo {
        [ExecuteMe]
        public static void M1() {
            Console.WriteLine("M1");
        }

        [ExecuteMe(45)]
        [ExecuteMe(0)]
        [ExecuteMe(3)]
        public void M2(int a) {
            Console.WriteLine("M2 a={0}", a);
        }

        [ExecuteMe("hello", "reflection")]
        public void M3(string s1, string s2) {
            Console.WriteLine("M3 s1={0} s2={1}", s1, s2);
        }

        [ExecuteMe("aaaaaa", 5)]
        [ExecuteMe(null, null)]
        public static void Repeat(string phrase, int times) {
            if (phrase == null) {
                Console.WriteLine("What?");
                return;
            }
            for (var i = 0; i < times; i++)
                Console.WriteLine(phrase);
        }
    }

    public class SumWithoutDefaultConstructor {
        private int Num1 { get; }
        private int Num2 { get; }

        public SumWithoutDefaultConstructor(int number1, int number2) {
            this.Num1 = number1;
            this.Num2 = number2;
        }
        [ExecuteMe(3, 4)]
        public void Sum() {
            Console.WriteLine($"{Num1}+{Num2} = {Num1+Num2}");
        }
    }

    public class SumWithDefaultConstructor {
        private int Num1 { get; }
        private int Num2 { get; }

        public SumWithDefaultConstructor() {}
        public SumWithDefaultConstructor(int number1, int number2) {
            this.Num1 = number1;
            this.Num2 = number2;
        }
        [ExecuteMe(3, 4)]
        public void Sum() {
            Console.WriteLine($"{Num1}+{Num2} = {Num1 + Num2}");
        }
    }

    public class Squared {
        [ExecuteMe("tre")]
        public static void Calculate(int number) {
            Console.WriteLine($"{number}*{number} = {number*number}");
        }

        [ExecuteMe]
        public static void Print() {
            Console.WriteLine("Hello");
        }

        [ExecuteMe(3)]
        public void refCalculate(ref int number) {
            number *= number;
        }

    }
}