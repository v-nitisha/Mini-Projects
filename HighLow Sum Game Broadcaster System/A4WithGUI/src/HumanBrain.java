/*
 * CSCI213 Assignment 2
 * --------------------------
 * File name: HumanBrain.java
 * Author: V Prathyaksha
 * Student Number: 5283048
 * Description: Reads input value for H or S
 * which decides if the player continues the game or not
 */

public class HumanBrain implements BrainInterface{

	public char nextMove(int cardsValue) {
		// TODO Auto-generated method stub
		return  Keyboard.readChar("[H]it or [S]tand >");
	}
}
