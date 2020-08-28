/*
 * CSCI213 Assignment 2
 * --------------------------
 * File name: AdminData.java
 * Author: V Prathyaksha
 * Student Number: 5283048
 * Description: Contains all other miscellaneous 
 * functions needed to handle the Player data
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


// Open players.dat and imports players to playerList arrayList
public class AdminData{
    public ArrayList<Player> playerList;
   
    public AdminData(){
    
	playerList = new ArrayList<Player>();
    
    try{
        Scanner fileScanner = new Scanner(new File("players.dat"));
        while(fileScanner.hasNextLine())
        {   
            String data = fileScanner.nextLine();
            String [] dataArray = data.split("\\|");
            String loginName = dataArray[0];
            String hashedPassword = dataArray[1];
            String chipsString = dataArray[2];
            int chips = Integer.parseInt(chipsString);
            String name = dataArray[3];
            String email = dataArray[4];
            String birthdate = dataArray[5];
            
            Player p = new Player(loginName,hashedPassword,chips,name,email,birthdate);
            playerList.add(p);
        }
        fileScanner.close();
	
	    }
	    catch(FileNotFoundException ex)
	    {
	        System.out.println("players.dat not found.");
	        
	    }
	}
	public void updateAdminDataToFile()
	{
	    try{
	    
	        PrintWriter fileOut = new PrintWriter("players.dat");
	        
	        for(Player p:playerList)
	        {
	            String loginName = p.getLoginName();
	            String hashedPassword = p.getHashedPassword();
	            String chipsString = p.getChips()+"";
	            String name = p.getName();
	            String email = p.getEmail();
	            String birthday = p.getBirthdate();
	            
	            String format = loginName+"|"+hashedPassword+"|"+chipsString+"|"+name+"|"+email+"|"+birthday;
	            fileOut.println(format);
	            
	        }
	        
	        fileOut.close();
	        
	    }catch(IOException e)
	    {
	        System.out.println("Unable to open admin.dat for writing.");
	        
	    }
	    
	}
	public Player getPlayer(String loginName)
	{
	    Player player=null;
	    
	    for(Player p:playerList)
	        {
	            if(p.getLoginName().equals(loginName))
	            {
	                
	                player = p;
	            }
	            
	        }
	    
	    return player;
	}
	public boolean checkAccount(String username, String password){
		boolean status = true;
		for(Player p:playerList)
        {
			// Check username
            if(p.getLoginName().equals(username))
            {
                // Check password
            	boolean pwCheck = true;
            	p.checkPassword(password);
    	        if(pwCheck){
    	        	return status;
    	        }
    	        else{
    	        	status = false;
    	        }
            }
            else{
            	status = false;
            }
        }
		return status;
	}

	@Override
 	public String toString() {
		return "AdminData [playerList=" + playerList + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
}
