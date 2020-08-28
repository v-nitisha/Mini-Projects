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

void Insert(string key, int n, nodeptr & chord) {

	long int hashid = Hash(key,n);
	nodeptr cur = chord;
	long int store = chord->ID;
	bool loop = true, found = false, storeRes = false;
	
	string path;
	int count = 0;
	
	while(loop){
		
		if(count == 0){
			path = convertToString(cur->ID);
		}else {
			path = path + ">" + convertToString(cur->ID);
		}

		if(cur->ID >= hashid){

			store = cur->ID;
			cur->resource.insert(pair<long int, string>(hashid, key));
			cout << "INSERTED " << key << " (key=" << hashid << ") AT " << store << endl;
			storeRes = true;
			loop = false;
			found = true;
		}else {
		
			for(int i = 0; i < n; i++){
				if(cur->fingertable[i] >= hashid){
					store = cur->fingertable[i];
					found = true;
					storeRes = false;
					break;
				}
			}
		}
		
		if(cur->next == NULL){
			loop = false;
		}else {
			cur = cur->next;
		}
		count = 1;
	}

	if(!found){
		chord->resource.insert(pair<long int, string>(hashid, key));
		cout << "INSERTED " << key << " (key=" << hashid << ") AT " << chord->ID << endl;
	}
	cout << path <<endl;	
}
