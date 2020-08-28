/*
 * CSCI213 Assignment 2
 * --------------------------
 * File name: GameView.java
 * Author: V Prathyaksha
 * Student Number: 5283048
 * Description: Does all display related functions
 */
import java.util.*;

public class GameView {
	
	private ViewTransmitter tx;
	
	public GameView(ViewTransmitter tx){
		this.tx = tx;
	}

	public void displayIntro(){
		
		String s = "Game Starts\nDealer shuffles cards";
		System.out.println(s);
		tx.sentMessage(s);
	}
	public void displayRound(int round){
		String s = "Dealer dealing cards - ROUND " + round + "\n";
		System.out.println(s);
		tx.sentMessage(s);
	}
	public void displayGetChips(int chips){
		String s = "Player has " + chips + " chips";
		System.out.println(s);
		tx.sentMessage(s);
	}
	public void printChips(int chips){
		String s = "Total chips for Player is: " + chips + "\n";;
		System.out.println(s);
		tx.sentMessage(s);
	}
	public void displayEnding(){
		String s = "Thank you for playing odd and even game";
		System.out.println(s);
		tx.sentMessage(s);
	}
	
	public void print(String s){
		System.out.println(s);
		tx.sentMessage(s);
	}
	public void updateTable(ArrayList<GamePlayerInterface> players, GamePlayerInterface player){
		
		for(GamePlayerInterface p: players){
			
			if(!p.getLoginName().equals(player.getLoginName()))
					showPlayerCards(p);
		}
		
		showPlayerCards(player);
	}
	public void updateTableShowAllCards(ArrayList<GamePlayerInterface> players){
		
		for(GamePlayerInterface p: players){
			
			showAllPlayerCard(p);
		}
	}
	//this will show the hidden card too
	public void showAllPlayerCard(GamePlayerInterface player){
		
		System.out.println(player.getLoginName());
		ArrayList<Card> cards = player.getCardsOnHand();
		
		for(Card card:cards){
			
				System.out.print(card+" ");
				
		}
		
		System.out.println();
		System.out.print("Value    :"+player.getTotalCardsValue()); 
		System.out.println();
	}
	//this will not show the hidden card
	public void showPlayerCards(GamePlayerInterface player){
		
		System.out.println(player.getLoginName());
		ArrayList<Card> cards = player.getCardsOnHand();
		int count = 0;
		
		for(Card card:cards){
			
			if(!player.isHuman() && count == 0){
				System.out.print("<HIDDEN CARD> ");
				count++;
			}
			else
				System.out.print(card+" ");
				
		}
		
		if(player.isHuman()){
			System.out.println();
			System.out.print("Value    :"+player.getTotalCardsValue()); 
		}
		
		System.out.println();
		
	}
	public void printMessage(String message){
		System.out.println(message);
		tx.sentMessage(message);
	}
	public void printLine(){
		Keyboard.line(50, "-");
	}
}
