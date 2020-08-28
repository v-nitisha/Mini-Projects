/*
V Prathyaksha
5283048
*/

#include<iostream>
#include<map>
#include<vector>
#include<string>
#include<fstream>
#include<sstream>
#include "functions.h"
using namespace std;

void InitChord(long int n, long int ID, int size, nodeptr & chord){
	
	if(chord != NULL){
		reinitialise(chord, n, ID, size);
	}else {
		nodeptr tmp = createNode(ID, size);
		string path;
		for(int i = 0; i < size; i++){
			tmp->fingertable[i] = tmp->ID;
		}
	
		chord = tmp;
		cout << "V Prathyaksha" << endl;
		cout << "5283048" << endl;
		path = convertToString(chord->ID);
		cout << path << ">" << path << endl;
	
		fingerTable(chord, chord, ID, size);
	}
}

void reinitialise(nodeptr & chord, long int chordSize, long int ID, int size){
	
	nodeptr temp;
		
		while(chord->next != NULL){
		
			temp = chord;
			chord = chord->next;
			delete [] temp->fingertable;
			temp->resource.clear();
			delete temp;
		}
		
		delete [] chord->fingertable;
		chord->resource.clear();
		delete chord;

		chord = NULL;
		
		InitChord(chordSize, ID, size, chord);	
}
