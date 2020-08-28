package com.gen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class Gen {

	
	public String generatePrimeNumber() {
		
		Random rand = new SecureRandom(); 
		BigInteger x = BigInteger.probablePrime(10, rand);// due memory issues currently calculating p upto 10 bits
		return x.toString();
				
	}
	
	// taken from old assignment and make some adjustments as per required.
	private static String gGenerator(String p)
	{
	
		BigInteger pInteger = new BigInteger(p);
		int intP = Integer.valueOf(p);
		int ZpSet[] = new int[intP];
		int g = 0;
		for (int a = 1; a < intP; a++)
			
		{
			BigInteger aBigInteger = new BigInteger(String.valueOf(a));
			for (int i = 0; i < intP-1; i++)
			{
				
				BigInteger x = aBigInteger.pow(i);
				x = x.mod(new BigInteger(String.valueOf(intP))) ;
				
				// ADD VALUES INTO ARRAY
				ZpSet[i] = x.intValue();
				
			}
			
			
			// CHECK IF EVERY VALUE IN ARRAY IS UNIQUE
			boolean isUnique = true;
			for(int i = 0; i < ZpSet.length; i++)
			{
				for (int j = i+1; j < ZpSet.length; j++)
				{
					if(ZpSet[i] == ZpSet[j])
					{
						isUnique = false;
						break;
					}
				}
				if (!isUnique) {
					break;
				}
			}
			if (isUnique == true)
			{
				g = a;
				System.out.println("Primitive root: " + g);
				break;
			}

	 }
		return String.valueOf(g);
	}
	
	
	
	
	// this method writes the data to the file.
	public void writeToFile(String password ,String p , String g) {
		
		
		File oufile = new File("sharedCredentials.txt");
		try(FileWriter fWriter = new FileWriter(oufile);
			BufferedWriter bWriter = new BufferedWriter(fWriter))
		{
			bWriter.write(password + "," + p + "," + g);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void main(String[] args) {
		Gen gen = new Gen();
		String password = "126435";
		String p = gen.generatePrimeNumber() ;
		String g = gGenerator(p);
		
		gen.writeToFile(password,p, g);
		
		int x =((int)Math.pow(3, 6))% 571;
		System.out.println("x: " + x);
		
		
		
	}

}
