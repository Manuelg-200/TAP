#include <iostream>
#include <cmath>
#include "funzioni.h"
using namespace std;

int main() {
	//Prima matrice
	
	int n = 4;
	float** A;
	float *b;
	float *x;
	A = initMatrice(n, n);
	const float a1[n*n] = {3, 1, -1, 0, 0, 7, -3, 0, 0, -3, 9, -2, 0, 0, 4, -10};	//Array degli elementi della prima matrice
	SetMatrice(A, n, a1);
	b = TermineNoto(A, n);
	cout << "Il termine noto della prima matrice con vettore delle soluzioni uguale a 1 e' b = ";
	stampaVettore(b, n);
	Gauss(A, b, n, n);
	x = SostituzioneIndietro(A, b, n, n);
	cout << "Soluzioni del sistema Ax = b: ";
	stampaVettore(x, n);
	
	delete A;
	delete b;
	delete x;
	
	//Seconda matrice
	A = initMatrice(n, n);
	const float a2[n*n] = {2, 4, -2, 0, 1, 3, 0, 1, 3, -1, 1, 2, 0, -1, 2, 1};	//Array degli elementi della seconda matrice
	SetMatrice(A, n, a2);
	b = TermineNoto(A, n);
	cout << "\nIl termine noto della seconda matrice con vettore delle soluzioni uguale a 1 e' b = ";
	stampaVettore(b, n);
	Gauss(A, b, n, n);
	x = SostituzioneIndietro(A, b, n, n);
	cout << "Soluzioni del sistema Ax = b: ";
	stampaVettore(x, n);
	
	delete A;
	delete b;
	delete x;
	
	//Matrice di Pascal
	n = 10;
	A = setPascal(n);
	b = TermineNoto(A, n);
	cout << "\nIl termine noto della matrice di pascal 10x10 con vettore delle soluzioni uguale a 1 e' b = ";
	stampaVettore(b, n);
	Gauss(A, b, n, n);
	x = SostituzioneIndietro(A, b, n, n);
	cout << "Soluzioni del sistema Ax = b : ";
	stampaVettore(x, n);
	
	delete A;
	delete b;
	delete x;
	
	//Matrice Tridiagonale
	int d0 = 5, d1 = 4;	//Matricola 4696045
	n = 10*(d1 + 1) + d0;
	A = setTridiagonale(n);
	b = TermineNoto(A, n);
	cout << "\nIl termine noto della matrice tridiagonale con vettore delle soluzioni uguale a 1 e' b = ";
	stampaVettore(b, n);
	Gauss(A, b, n, n);
	x = SostituzioneIndietro(A, b, n, n);
	cout << "Soluzioni del sistema Ax = b : ";
	stampaVettore(x, n);
	
	return 0;
}