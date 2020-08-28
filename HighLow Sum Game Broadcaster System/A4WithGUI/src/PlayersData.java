/*
 * CSCI213 Assignment 2
 * --------------------------
 * File name: PlayersData.java
 * Author: V Prathyaksha
 * Student Number: 5283048
 * Description: Contains all other miscellaneous 
 * functions needed to handle the Player data
 */

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


// Open players.dat and imports players to playerList arrayList
@SuppressWarnings("serial")
public class PlayersData extends JFrame {
    public ArrayList<Player> playerList;
    
    JPanel pnlNO = new JPanel();
    JPanel pnlYES = new JPanel();
    JLabel labelNO = new JLabel("Login NOT successful");
    JLabel labelYES = new JLabel("Login successful");
    
    Font customFont = new Font("Serif", Font.PLAIN, 32);
   
    public PlayersData(){
    
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
	public void updatePlayerDataToFile()
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
	        System.out.println("Unable to open players.dat for writing.");
	        
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
	public int checkWhichNumber(String username){
		int i = 0;
		
		for(Player p : playerList){
			if(p.getLoginName().equals(username)){
				return (i);
			}
			i++;
		}
		System.out.println("Error found at checkWhichNumber() function!"); 
		return 0;
	}
	public boolean loginUnsuccessful(){
		setSize(500, 200);
		add(pnlNO);
		pnlNO.add(labelNO);
		labelNO.setFont(customFont);
		setVisible(true);
		
		return false;
	}
	
	public boolean loginSuccessful(){
		setSize(500, 200);
		add(pnlYES);
		pnlYES.add(labelYES);
		labelYES.setFont(customFont);
		setVisible(true);
		
		return true;
	}
	
	
	@Override
	public String toString() {
		return "PlayersData [playerList=" + playerList + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
}
