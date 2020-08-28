/*
 * CSCI213 Assignment 2
 * --------------------------
 * File name: GamePlayerInterface.java
 * Author: V Prathyaksha
 * Student Number: 5283048
 * Description: An interface to implement
 * the functions in GamePlayer
 */import java.util.ArrayList;

public interface GamePlayerInterface {
	
	public void addCard(Card card);
	public int getTotalCardsValue();
	public ArrayList<Card> getCardsOnHand();
	public void clearCardsOnHand();
	public char nextMove();
	public String getLoginName();
	public void setStand();
	public boolean isStand();
	public boolean isHuman();
	public int getNumberOfCardsOnHand();
	public void setChips(int chips);
	public int getChips();
	public void deductChips(int chips);
	
}
