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

long int FindKey(string key, long int n, nodeptr & chord){

	long int hashid = Hash(key,n);
	bool loop = true;
	bool found = false;
	nodeptr cur = chord;
	long int storeID = -1;
	
	while(loop){
					
		found = check_resource(cur, hashid);
		if(found){
			storeID = cur->ID;
			break;
		}
		
		if(cur->next == NULL){
			loop = false;
		}else {
			cur = cur->next;
		}
	}
	
	return storeID;
}

bool check_resource(nodeptr & chord, long int hashid){

	nodeptr cur = chord;
	bool found = false;
	
	std::multimap<long int,string>::iterator it;
	
	for (it=cur->resource.begin(); it!=cur->resource.end(); ++it){
		if((*it).first == hashid){

		    found = true;
		    break;
		}
	}
    
    return found;
}
