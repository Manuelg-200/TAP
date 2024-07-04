#include <iostream>
#include <cmath>
using namespace std;

const int d0 = 5, d1 = 4;	//Matricola 4696045

int main()
{
	double a, result1, result2;
	double b = (d1+1)*pow(10, 20);
	double c = -b;
	
	for(int i = 0; i<=6; i++) {
		a = (d0+1)*pow(10, i);
		cout << "\na = " << a << "\n";
		result1 = (a + b) + c;
		result2 = a + (b + c);
		cout << "(a + b) + c = " << result1 << "\n";
		cout << "a + (b + c) = " << result2 << "\n";
	}
	return 0;
}