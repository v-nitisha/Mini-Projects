#include <iostream>  
using namespace std;  

int main()  
{  
	int n, r, sum = 0, temp;    
	
	cin >> n; 
	cout << n;   
	temp = n;    

	while(n > 0)    
	{    
		r = n % 10;    
		sum = (sum * 10) + r;    
		n = n / 10;    
	}    
	
	if(temp == sum)    
		cout << " is a palindrome";    
	else    
		cout << " is NOT a palindrome";   
	
	return 0;  
} 