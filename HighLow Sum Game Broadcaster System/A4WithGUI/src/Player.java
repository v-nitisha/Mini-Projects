/*
 * CSCI213 Assignment 2
 * --------------------------
 * File name: Player.java
 * Author: V Prathyaksha
 * Student Number: 5283048
 * Description: Has all the getters and setters for each Player
 */

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Player extends User {
    
    protected int chips;
    protected String name;
    protected String email;
    protected Calendar birthdate;
    
    public Player(String loginName,String hashedPassword,int chips, 
    				String name,String email, String birthdate)
    {
        super(loginName,hashedPassword);
        this.chips=chips;
        this.name = name;
        this.email = email;

        String[] dateElements = birthdate.split("-");
        int year = Integer.parseInt(dateElements[0]);
        int month = Integer.parseInt(dateElements[1]);
        int day = Integer.parseInt(dateElements[2]);
        this.birthdate = new GregorianCalendar(year,month-1,day);
    }
    public Player(String loginName,String hashedPassword,int chips, 
    				String name,String email, Calendar birthdate)
    {
        super(loginName,hashedPassword);
        this.chips=chips;
        this.name = name;
        this.email = email;
        this.birthdate = birthdate;
    }    
    public int getChips()
    {
        return chips;
    }
    public void addChips(int chips)
    {
        this.chips+=chips;
    }
    public void deductChips(int chips)
    {
        this.chips-=chips;
    }
    public void setChips(int chips)
    {
        this.chips = chips;
    }
	public void setBirthdate(Calendar birthdate) {
		this.birthdate = birthdate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirthdate(){
		return birthdate.get(Calendar.YEAR)+"-"+(birthdate.get(Calendar.MONTH)+1)+"-"+birthdate.get(Calendar.DAY_OF_MONTH);
	}
}
