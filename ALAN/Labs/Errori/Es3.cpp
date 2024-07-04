#include <iostream>
#include <math.h>

using namespace std;

double double_eps() {
	int d = 1;
	double eps = 1;		//Inizializzo il valore di eps a 1 per poter entrare nel ciclo while
	while ( (1 + eps) > 1) {
		eps = pow(2, -d);
		d++;
	}
	return eps;
}

float float_eps() {
	int d = 1;
	float eps = 1;
	while ( (1 + eps) > 1) {
		eps = powf(2, -d);
		d++;
	}
	return eps;
}


int main() {
	cout<<"Precisione di macchina double = " << double_eps() << "\n";
	cout<<"Precisione di macchina float = " << float_eps() << "\n";
	return 0;
}