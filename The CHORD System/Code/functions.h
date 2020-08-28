/*
V Prathyaksha
5283048
*/


#include<iostream>
#include<map>
#include<vector>
#include<string>
using namespace std;

struct node;
typedef node * nodeptr;

struct node {
	long int ID;
	multimap<long int, string> resource;
	nodeptr next;
	nodeptr prev;
	long int * fingertable;
};

// Add Function
void AddPeer(long int ID, long int n, nodeptr & chordsys);
void greaterIndexSwap(nodeptr & cur, nodeptr & chord, nodeptr & tmp, nodeptr & pres);
void lesserIndexSwap(long int ID, nodeptr & cur, nodeptr & chord, nodeptr & pres, nodeptr & tmp);
void simpleIndexSwap(nodeptr & cur, nodeptr & tmp, nodeptr & pres);
void checkAddedPeers(nodeptr & newNode, nodeptr & chord);

// Delete Function
void Delete(string hash, long int n, nodeptr & chord);
void returnPeer(nodeptr & positionPointer, nodeptr & chord, long int ID);

// Find Function
long int FindKey(string key, long int n, nodeptr & chord);
bool check_resource(nodeptr & chord, long int hashid);

// Initialise Function
void InitChord(long int n, long int ID, int size, nodeptr & chord);
void reinitialise(nodeptr & chord, long int chordSize, long int ID, int size);

// Insert Function
void Insert(string key, int n, nodeptr & chord);

// Output Function
void Print(long int ID, long int n, nodeptr & chord);
void outputResources(nodeptr & cur);

// Read Function
void Read(string filename, nodeptr & chord, int n, long int size);
int check_command(string val);
void construct_command(int commandID, string value, string & commandVal);
void execute_command(int commandID, string commandVal, nodeptr & chord, int n, long int size);

// Remove Function
void RemovePeer(long int ID, long int size, nodeptr & chord);
void moveDeletedResource(nodeptr & cur, nodeptr & chord, long int ID, bool addPeer);
void deleteGreaterIndex(nodeptr & cur, nodeptr & storeBack, nodeptr & chord);
void deleteLesserIndex(nodeptr & cur, nodeptr & store);

// Other Common Functions
unsigned int Hash (string dataitem, long int n);
long int pow(int);
vector<string> split(const string &s, char delim);
string convertToString(long val);
void fingerTable(nodeptr & curPeer, nodeptr & chord, long int ID, long int size);
nodeptr createNode(long int ID, long int size);

