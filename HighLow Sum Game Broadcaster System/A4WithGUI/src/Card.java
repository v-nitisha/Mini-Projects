/*
 * CSCI213 Assignment 2
 * --------------------------
 * File name: Card.java
 * Author: V Prathyaksha
 * Student Number: 5283048
 * Description: Initialized new card from 
 * Deck.java
 */

public class Card {

	private String suit;
	private String name;
	private int value;

	public Card(String s, String n, int v) {
		suit = s;
		name = n;
		value = v;
	}

	public String getSuit() {
		return suit;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public String toString() {
		return "<" + suit + " " + name + " " + value + ">";
	}

}
