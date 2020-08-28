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

void Print(long int ID, long int n, nodeptr & chord){
	
	long int hashid;
	nodeptr pos;

	returnPeer(pos, chord, ID);
	
	cout << "NODE: " << ID << endl;
	cout << "DATA AT NODE " << ID << ":" << endl;
	outputResources(pos);
	cout << "FINGER TABLE OF NODE " << ID << endl;

	for(int i = 0; i < n; i++){
		cout << pos->fingertable[i] << " ";
	}
	cout << endl;
}

void outputResources(nodeptr & cur){

	nodeptr pos = cur;
	
	std::multimap<long int,string>::iterator it;
	
	 for (it=cur->resource.begin(); it!=cur->resource.end(); ++it)
	    std::cout << (*it).first << " => " << (*it).second << '\n';
	
}
