/*
 * CSCI213 Assignment 2
 * --------------------------
 * File name: Dealer.java
 * Author: V Prathyaksha
 * Student Number: 5283048
 * Description: Initializes dealer and has 
 * all the functions a dealer needs such as
 * shuffling and dealing
 */


public class Dealer extends GamePlayer {

	Deck deck;

	public Dealer() {
		super("Dealer", "", 0,false, "dName", "dealer@gaming.com", "12-12-2012");
		deck = new Deck();
	}

	public void shuffleCards() {

		deck.shuffle();
	}

	public Card dealCard() {
		return deck.dealCard();
	}
}
