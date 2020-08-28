/*
 * CSCI213 Assignment 2
 * --------------------------
 * File name: GamePlayer.java
 * Author: V Prathyaksha
 * Student Number: 5283048
 * Description: For every player created, the
 * functions to modify the individual records,
 * especially related to the cards in the 
 * arraylist
 */
 
import java.util.*;

public class GamePlayer extends Player implements GamePlayerInterface{

	private ArrayList<Card> cardsOnHand;
	private boolean stand;
	private boolean isHuman;
	private BrainInterface brain;
	
	public GamePlayer(String loginName, String hashedPassword, int chips,boolean isHuman,
							String name, String email, String birthdate) {

		super(loginName, hashedPassword, chips, name, email, birthdate);
		this.isHuman = isHuman;
		cardsOnHand = new ArrayList<Card>();
		stand = false;
		
	}
	public GamePlayer(String loginName, String hashedPassword, int chips,
			String name, String email, String birthdate) {

		super(loginName, hashedPassword, chips, name, email, birthdate);
		this.isHuman = true;
		cardsOnHand = new ArrayList<Card>();
		stand = false;
		
	}
	public void setChips(int chips)
    {
        this.chips = chips;
    }
	public int getChips()
    {
        return chips;
    }
	public void setBrain(BrainInterface brain) {
		this.brain = brain;
	}
	public void addCard(Card card) {
		cardsOnHand.add(card);
	}
	public void deductChips(int chips)
    {
        this.chips-=chips;
    }
	public int getTotalCardsValue() {
		int totalValue = 0;

		for (Card c : cardsOnHand) {

			totalValue += c.getValue();
		}
		
		return totalValue;
	}
	public ArrayList<Card> getCardsOnHand() {

		return cardsOnHand;

	}
	public int getNumberOfCardsOnHand(){
		
		return cardsOnHand.size();
	}
	public void clearCardsOnHand() {
		cardsOnHand.clear();
	}
	public char nextMove(){
		//no error check here
        return brain.nextMove(getTotalCardsValue());
    }
	public void setStand(){
		stand = true;
	}
	public boolean isStand(){
		return stand;
	}
	public boolean isHuman(){
		return isHuman;
	}
}
