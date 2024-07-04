#include <iostream>
#include <cmath>
#include "funzioni.h"
using namespace std;

//Funzione che calcola la norma infinito di un vettore
float NormaInfinitoVect(float* v, int dim) {
	float max = 0;
	for(int i=0; i<dim; i++) {
		if(fabs(v[i]) > max)
			max = fabs(v[i]);
	}
	return max;
}

//Funzione che inizializza il termine noto perturbato (bTilde)
float *TerminePerturbato(float* b, int dim) {
	float* bTilde = new float [dim];
	float norma = NormaInfinitoVect(b, dim);
	float deltab;
	for(int i=0; i<dim; i++) {
		deltab = norma;
		if( i%2 == 0)	//Applico le perturbazioni a deltaB ogni ciclo
			deltab *= -0.01;
		else
			deltab *= 0.01;
		bTilde[i] = b[i] + deltab;
	}
	return bTilde;
}

int main() {
	//Prima matrice
	int n = 4;
	float** A;
	float *b;
	float *bTilde;
	float *x;
	A = initMatrice(n, n);
	const float a1[n*n] = {3, 1, -1, 0, 0, 7, -3, 0, 0, -3, 9, -2, 0, 0, 4, -10};	//Array degli elementi della prima matrice
	SetMatrice(A, n, a1);
	b = TermineNoto(A, n);
	bTilde = TerminePerturbato(b, n);
	Gauss(A, bTilde, n, n);
	x = SostituzioneIndietro(A, bTilde, n, n);
	cout << "Soluzioni del sistema Ax = b : ";
	stampaVettore(x, n);
	
	delete A;
	delete b;
	delete bTilde;
	delete x;
	
	//Seconda matrice
	A = initMatrice(n, n);
	const float a2[n*n] = {2, 4, -2, 0, 1, 3, 0, 1, 3, -1, 1, 2, 0, -1, 2, 1};	//Array degli elementi della seconda matrice
	SetMatrice(A, n, a2);
	b = TermineNoto(A, n);
	bTilde = TerminePerturbato(b, n);
	Gauss(A, bTilde, n, n);
	x = SostituzioneIndietro(A, bTilde, n, n);
	cout << "Soluzioni del sistema Ax = b: ";
	stampaVettore(x, n);
	
	delete A;
	delete b;
	delete bTilde;
	delete x;
	
	//Matrice di Pascal
	n = 10;
	A = setPascal(n);
	b = TermineNoto(A, n);
	bTilde = TerminePerturbato(b, n);
	Gauss(A, bTilde, n, n);
	x = SostituzioneIndietro(A, bTilde, n, n);
	cout << "Soluzioni del sistema Ax = b : ";
	stampaVettore(x, n);
	
	delete A;
	delete b;
	delete bTilde;
	delete x;
	
	//Matrice Tridiagonale
	int d0 = 5, d1 = 4;	//Matricola 4696045
	n = 10*(d1 + 1) + d0;
	A = setTridiagonale(n);
	b = TermineNoto(A, n);
	bTilde = TerminePerturbato(b, n);
	Gauss(A, bTilde, n, n);
	x = SostituzioneIndietro(A, bTilde, n, n);
	cout << "Soluzioni del sistema Ax = b : ";
	stampaVettore(x, n);
	
	return 0;
}