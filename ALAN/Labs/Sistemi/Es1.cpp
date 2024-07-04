#include <iostream>
#include <cmath>
#include "funzioni.h"
using namespace std;

//Funzione che calcola la norma infinito di una matrice 4x4
int NormaInfinito(float **A, int dim) { 	
	int max = 0;	//Inizializzo il massimo a zero perchè è una sommatoria di valori assoluti e non sarà mai negativa
	for(int i=0; i<dim; i++) {
		int Sum = 0;
		for(int j=0; j<dim; j++)
			Sum += fabs(A[i][j]);	//Faccio la sommatoria con il modulo dei valori della riga
		if(max < Sum)	//Alla fine del ciclo interno, controllo se il risultato della sommatoria è maggiore del massimo trovato in precedenza
			max = Sum;
	}
	return max;
}

int main() {
	//Punto a
	float **A;	//Matrice definita come doppio puntatore
	int n;
	cout << "Inserire il numero di righe e colonne delle matrici quadrate: ";
	cin >> n;
	A = initMatrice(n, n);
	cout << "Inserire gli elementi della prima matrice uno per uno seguendo le righe: ";
	float a[n*n];	//Array degli elementi della matrice del punto a
	for(int i=0; i<n*n; i++)
		cin >> a[i];
	SetMatrice(A, n, a);
	cout << "La norma infinito della prima matrice e' " << NormaInfinito(A, n) << "\n";
	
	cout << "inserire gli elementi della seconda matrice uno per uno seguendo le righe: ";
	for(int i=0; i<n*n; i++)
		cin >> a[i];
	SetMatrice(A, n, a);
	cout << "La norma infinito della seconda matrice e' " << NormaInfinito(A, n) <<"\n";
	delete A;
	
	//Punto b
	n = 10;
	A = setPascal(n);
	cout << "La norma infinito della matrice di pascal " << n << "x" << n << " e' " << NormaInfinito(A, n) << "\n";
	delete A;
	
	//Punto c
	int d0 = 5, d1 = 4;	//Matricola 4696045
	n = 10*(d1 + 1) + d0;
	A = setTridiagonale(n);
	cout<< "La norma infinito della matrice tridiagonale "<< n << "x" << n << " e' " << NormaInfinito(A, n) << "\n";
	delete A;	
	
	return 0;
}