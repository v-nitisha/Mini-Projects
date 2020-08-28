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

void AddPeer(long int ID, long int size, nodeptr & chord){
	
	bool greater = false, ignore = false;
	int counter = 0;
	nodeptr tmp = createNode(ID, size);
	nodeptr cur = chord;
	nodeptr pres;
	string path;
	long int prev = 0;
	
	cout << "PEER " << ID << " ADDED" << endl;
	
	if(chord == NULL){
		path = convertToString(chord->ID);
		chord = tmp;
		cout << path << ">" << path << endl;
	}else {

		if(cur->next != NULL){
			
			path = convertToString(chord->ID);
			while(cur->next != NULL){				

				if(!ignore){

					if(cur->ID >= ID){
						greater = true;	
						break;
					}else {
						if(counter == 0){
							cout << cur->ID;
						}else {
							cout << ">" << cur->ID;
						}
						prev = cur->fingertable[0];
						if(prev < ID){
							ignore = true;
						}
					}
				}else {
					ignore= false;
				}
				cur = cur->next;
				counter = 1;
			}

			if(cur->ID > ID){
				greater = true;	
			}
			
			if(greater){
				greaterIndexSwap(cur, chord, tmp, pres);
			}else{
				simpleIndexSwap(cur, tmp, pres);
			}
		}else {
			lesserIndexSwap(ID, cur, chord, pres, tmp);
		}
	
	}

	fingerTable(pres, chord, ID, size);
	checkAddedPeers(pres, chord);
	cout << endl;

}

void greaterIndexSwap(nodeptr & cur, nodeptr & chord, nodeptr & tmp, nodeptr & pres){
	
	nodeptr store = NULL;
	
	if(cur->prev != NULL){
		store = cur->prev;
		cur->prev = tmp;
		tmp->prev = store;
		store->next = tmp;
		tmp->next = cur;

		pres = store->next;

		cout << ">" << tmp->ID;
	}else {
	
		nodeptr mine = chord;
		chord = tmp;
		chord->next = mine;
		mine->prev = chord;
		
		cout << chord->ID << ">" << mine->ID;
		pres = chord;
	}	
}

void lesserIndexSwap(long int ID, nodeptr & cur, nodeptr & chord, nodeptr & pres, nodeptr & tmp){

	nodeptr store = NULL;
	
	if(cur->ID > ID){

		store = chord;
		chord = tmp;
	
		chord->next = store;
		chord->prev = NULL;
		store->prev = chord;
		store->next = NULL;
		
		pres = store;
		
		cout << chord->ID << ">" << chord->next->ID;
		
	}else {
		chord->next = tmp;
		tmp->prev = chord;
		
		pres = chord->next;
		cout << chord->ID << ">" << pres->ID;
	}
}

void simpleIndexSwap(nodeptr & cur, nodeptr & tmp, nodeptr & pres){

	cur->next = tmp;
	tmp->prev = cur;
	
	pres = cur->next;
	cout << ">" << cur->ID;
}

void checkAddedPeers(nodeptr & newNode, nodeptr & chord){

	nodeptr cur = chord;
	bool mod = false;
	bool loop = true;
	std::multimap<long int,string>::iterator it;
	std::multimap<long int,string>::iterator temp;

	while(loop){
		if(!cur->resource.empty()){

			for (it=cur->resource.begin(); it!=cur->resource.end();){
				if(cur->ID <= (*it).first){

					if(newNode->ID >= (*it).first){
						temp = it;
						++temp;
						newNode->resource.insert(pair<long int, string>((*it).first, (*it).second));
						
						it = cur->resource.erase(it);
						it = temp;
						mod = true;
					}else {
						 ++it;
					}
					
				}else {
					 ++it;
				}
			}
		}
		
		if(mod){
			break;
		}

		if(cur->next == NULL){
			loop = false;
		}else {
			cur = cur->next;
		}
	}
}
