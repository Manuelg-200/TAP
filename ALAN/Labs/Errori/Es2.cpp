#include <iostream>
#include <math.h>
using namespace std;

const int N[5] = {3, 10, 50, 100, 150};

double fattoriale(double n) {
	if(n == 0)
		return 1;
	return n*fattoriale(n-1);
}

double Algoritmo1(double X, int N) {	//In input: Punto X in cui calcolare la funzione, grado N del polinomio di taylor
	double somma = 0;
	double num, den;	//Numeratore, denominatore
	for(int n = 0; n<=N; n++) {
		num = pow(X, n);
		den = fattoriale(n);
		somma = somma + ( num / den );
	}
	return somma;
}

double Algoritmo2(double X, int N) {
	double Taylor = Algoritmo1(-X, N);	//Richiamo l'algoritmo 1 con l'opposto del punto x per poi calcolarne il reciproco
	return 1/Taylor;
}

void Errori(double valCalcolato, double x) {
	double valEsatto = exp(x);
	double absErr = valCalcolato - valEsatto;
	double relErr = absErr / valEsatto;
	cout<<"Errore assoluto = "<<absErr<<"\n";
	cout<<"Errore relativo = "<<relErr<<"\n";
}

int main() {
	double Risultato;
	double x;
	cout << "\nInserire il punto in cui calcolare l'esponenziale: ";
	cin >> x;
	cout<<"\nAlgoritmo 1:\n";
	for(int i = 0; i<5; i++) {
		Risultato = Algoritmo1(x, N[i]);
		cout<<"N = " << N[i] << " , f(" << x <<") = " << Risultato <<"\n"; 
		Errori(Risultato, x);
	}
	
	cout<<"\nAlgoritmo 2:\n";
	for(int i = 0; i<5; i++) {
		Risultato = Algoritmo2(x, N[i]);
		cout<<"N = " << N[i] << " , f(" << x <<") = " << Risultato <<"\n";
		Errori(Risultato, x);
	}
	
	return 0;
}