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

void Delete(string hash, long int n, nodeptr & chord){
	
	long int nodeID = FindKey(hash, n, chord);
	long int hashid = Hash(hash,n);
	nodeptr cur = chord;
	nodeptr pos;
	
	std::multimap<long int,string>::iterator it;
	std::multimap<long int,string>::iterator temp;
	
	returnPeer(pos, chord, nodeID);
	
	for (it=pos->resource.begin(); it!=pos->resource.end();){
		if(hashid == (*it).first){
			temp = it;
			++temp;

			cout << "REMOVED " << (*it).second << "(key=" << (*it).first << ") FROM " << pos->ID << endl;
			it = pos->resource.erase(it);
			it = temp;
		}else {
			 ++it;
		}
	}
}

void returnPeer(nodeptr & positionPointer, nodeptr & chord, long int ID){
	
	positionPointer = chord;
	bool loop = true;
	
	while(loop){

		if(positionPointer->ID == ID){
			break;
		}
		if(positionPointer->next == NULL){
			loop = false;
		}else {
			positionPointer = positionPointer->next;
		}	
	}
	
}
