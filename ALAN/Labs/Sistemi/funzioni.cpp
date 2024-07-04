#include <iostream>
#include <cmath>
#include "funzioni.h"
using namespace std;

//Funzione scambio
void swap(float& a, float& b) {
	float tmp = a;
	a = b;
	b = tmp;
}

//Funzione ricorsiva che calcola il fattoriale
float fattoriale(float n) {
	if(n == 0)
		return 1;
	return n*fattoriale(n-1);
}

//Funzione che inizializza una matrice allocata dinamicamente
float** initMatrice(int righe, int colonne) {
	float** matrice;
	matrice = new float *[colonne];
	for(int i=0; i<colonne; i++)
		matrice[i] = new float[righe];
	return matrice;
}

//Funzione che inserisce i valori da un vettore a una matrice nxn
void SetMatrice(float **Matrice, int dim, const float array[]) {
	int counter = 0;	//Contatore per il numero di elementi dal vettore
	for(int i=0; i<dim; i++)	{
		for(int j=0; j<dim; j++) {
			Matrice[i][j] = array[counter];
			counter++;
		}
	}
}

//Funzione che inizializza una matrice di pascal nxn
float** setPascal(int dim) {
	float** P = initMatrice(dim, dim);
	for(int i=1; i<=dim; i++) {
		for(int j=1; j<=dim; j++) 	//I cicli for partono da 1 per evitare fattoriali di numeri negativi
			P[i-1][j-1] = fattoriale(i+j-2) / (fattoriale(i-1)*fattoriale(j-1));
	}
	return P;
}

//funzione che inizializza una matrice trdiagonale nxn
float** setTridiagonale(int dim) {
	float** T = initMatrice(dim, dim);
	for(int i=1; i<=dim; i++) {
		for(int j=1; j<=dim; j++) {
			if( i == j )
				T[i-1][j-1] = 2;
			else if( fabs(i-j) == 1)
				T[i-1][j-1] = 1;
			else
				T[i-1][j-1] = 0;
		}
	}
	return T;
}

//Funzione che calcola il termine noto di un sistema assumendo che la soluzione sia un vettore di 1
float* TermineNoto(float** Matrice, int dim) {
	float* b = new float [dim];	//Vettore termine noto
	for(int i=0; i<dim; i++) {
		b[i] = 0;	//Inizializzo a 0 per fare la sommatoria degli elementi della riga i della matrice, essendo un prodotto riga colonna per un vettore di 1
		for(int j=0; j<dim; j++) {
			b[i] += Matrice[i][j]; 
		}
	}
	return b;
}

//Funzione che stampa un vettore
void stampaVettore(float *vect, int dim) {
	cout << "(";
	for(int i=0; i<dim; i++)
		cout << vect[i] << ", ";
	cout << ")\n";
}

//Funzione ausiliaria alla funzione Gauss che svolge il pivoting parziale
void PivotingParziale(float** Matrice, float* Termine, int righe, int colonne, int k) {
	int max = 0, i0;	//Inizializzo il massimo a zero perchè cerchiamo il modulo più alto
	for(int i=k; i<righe; i++) {	//Cerco il massimo in modulo degli elementi della colonna partendo dal pivot
		if( max < fabs(Matrice[i][k]) ) {
			max = Matrice[i][k];
			i0 = i;	//Salvo l'indice del nuovo pivot
		}
	}
	if(i0 != k)	//Se devo fare lo scambio di righe
	{
		swap(Termine[k], Termine[i0]);	//Scambio righe termine noto
		for(int i=0; i<colonne; i++)	//Scambio righe matrice
			swap(Matrice[k][i], Matrice[i0][i]);
	}	
}

//Funzione che applica l'algoritmo gaussiano a una matrice di dimensioni arbitriarie
void Gauss(float** Matrice, float* Termine, int righe, int colonne) {
	float m;
	int numeroPivot;	//Se la matrice è di dimensioni arbitrarie il numero di pivot sarà uguale al numero più basso tra righe e colonne
	if(righe <= colonne)
		numeroPivot = righe;
	else
		numeroPivot = colonne;
	for(int k=0; k<numeroPivot; k++) {	//Primo ciclo che scorre i pivot
		PivotingParziale(Matrice, Termine, righe, colonne, k);
		if(Matrice[k][k] != 0) {	//Se il pivot è diverso da zero
			for(int i=k+1; i<righe; i++) {	//Secondo ciclo che scorre nella colonna del pivot
				m = Matrice[i][k] / Matrice[k][k];	//Calcolo del multiplo
				Termine[i] -= ( m*Termine[k] );	//Aggiornamento del termine noto
				for(int j=k+1; j<colonne; j++) 	//Terzo ciclo per aggiornamento della riga
					Matrice[i][j] -= ( m*Matrice[k][j] );
			}
		}
	}
}

//Funzione che svolge la sostituzione all'indietro e restituisce la soluzione del sistema
float* SostituzioneIndietro(float** Matrice, float* Termine, int righe, int colonne) {
	float* x = new float [colonne];	//Vettore delle soluzioni
	for(int i=colonne-1, j=righe-1; i>=0 && j>=0; i--, j--) {	//Ciclo per il vettore delle soluzioni, tiene conto sia delle righe che delle colonne
			x[i] = Termine[j] / Matrice[j][i];
			for(int k=0; k<j; k++)	//Ciclo per moltiplicare la soluzione trovata nella matrice
				Termine[k] -= (Matrice[k][i]*x[j]); 
	}
	return x;
}