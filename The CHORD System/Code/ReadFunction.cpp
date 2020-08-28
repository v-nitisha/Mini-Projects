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

void Read(string filename, nodeptr & chord, int n, long int size){

	ifstream file;
	file.open(filename);
	string line = "";
	vector<string> vals;
	string command = "", commandVal = "", value = "";
	bool ignore = false, first = true;
	
	int commandID = -1;
	
	if(file.fail()){
		file.clear();
		file.close();
		return;
	}else {
		while(!file.eof()){
			getline(file,line);
			vals = split(line, ' ');
			for (std::vector<string>::iterator it = vals.begin(); it != vals.end(); ++it){
				if(first){
					command = *it;
					commandID = check_command(command);
					first = false;
				}else {
					value = *(it);
					if(!ignore){
						if(value[0] == '#'){
							ignore = true;
						}else if(commandID == -1){
							ignore = true;
						}
						if(!ignore){
							construct_command(commandID, value, commandVal);
						}
					}
				}
			}
			
			if(commandID != -1){
				execute_command(commandID, commandVal, chord, n, size);
			}
			commandID = -1;
			first = true;
			ignore = false;
			commandVal = "";
			vals.clear();

		}
	}
	
	file.close();	
}

int check_command(string val){

	if(val == "initchord"){
		return 0;
	}
	if(val == "addpeer"){
		return 1;
	}
	if(val == "removepeer"){
		return 2;
	}
	if(val == "insert"){
		return 3;
	}
	if(val == "print"){
		return 4;
	}
	if(val == "delete"){
		return 5;
	}

	return -1;
}

void construct_command(int commandID, string value, string & commandVal){
	
	switch(commandID){
		case 0:
		case 1:
		case 2:
		case 4:
			commandVal = value;
		break;
		
		case 3:
		case 5:
			if(commandVal.length() > 0){
				commandVal = commandVal + " " + value;
			}else {
				commandVal = value;
			}
		break;
	}
}

void execute_command(int commandID, string commandVal, nodeptr & chord, int n, long int size){

	long int ID;
	
	switch(commandID){
		case 0:
			ID = stol(commandVal);
			InitChord(size, ID, n, chord);
		break;
		
		case 1:
			ID = stol(commandVal);
			AddPeer(ID, n, chord);
		break;
		
		case 2:
			ID = stol(commandVal);
			RemovePeer(ID, n, chord);
		break;
		
		case 3:
			Insert(commandVal, n, chord);
		break;
		
		case 4:
			ID = stol(commandVal);
			Print(ID, n, chord);
		break;
		
		case 5:
			Delete(commandVal, n, chord);
		break;

	}	
}
