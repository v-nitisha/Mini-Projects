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

void RemovePeer(long int ID, long int size, nodeptr & chord){

	cout << "PEER " << ID << " REMOVED" << endl;
	nodeptr cur = chord;
	nodeptr store, storeBack;
	bool found = false;
	
	long int storeID = chord->ID;
	
	if(cur->next == NULL){
		delete chord;
		chord = NULL;
	}else {
		
		while(cur->next != NULL){
			if(storeID >= cur->ID){

				if(cur->ID == ID){
					store = cur;
					found = true;
					if(cur->next != NULL){
						cout << cur->next->ID << ">" << cur->next->fingertable[0];
					}
					break;
				}else {
					for(int i = 0; i < size; i++){
						if(cur->fingertable[i] >= ID){
							storeID = cur->fingertable[i];
						}
					}
					cout << cur->ID << ">";
				}
			}
			cur = cur->next;	
		}
		
		moveDeletedResource(cur, chord, ID, false);
		
		if(found){
			deleteGreaterIndex(cur, storeBack, chord);
			fingerTable(storeBack, chord, ID, size);
		}else {
			deleteLesserIndex(cur, store);
			fingerTable(store, chord, ID, size);
		}
	}
	cout << endl;

}

void deleteGreaterIndex(nodeptr & cur, nodeptr & storeBack, nodeptr & chord){
	
	nodeptr store;
	
	if(cur->prev != NULL){
		store = cur->next;
		storeBack = cur->prev;
		delete cur;
		store->prev = storeBack;
		storeBack->next = store;
		
	}else {
		store = chord->next;				
		chord->resource.clear();
		delete chord;
		chord = store;
		chord->prev = NULL;
		
		storeBack = chord;
	}
}

void deleteLesserIndex(nodeptr & cur, nodeptr & store){
	
	store = cur->prev;
	cout << store->ID;
	store->next = NULL;
	delete cur;
}

void moveDeletedResource(nodeptr & cur, nodeptr & chord, long int ID, bool addPeer){

	bool loop = true;
	long int prev = 0;
	bool nextSearch = false;
	bool resource = false;
	std::multimap<long int,string>::iterator it;

	nodeptr search = NULL;
	long int nextID = cur->fingertable[0];
	
	
	if(!cur->resource.empty()){
	
		for (it=cur->resource.begin(); it!=cur->resource.end(); ++it){

				returnPeer(search, chord, nextID);
				search->resource.insert(pair<long int, string>((*it).first, (*it).second));
		}
	}
}
