/*
 * CSCI213 Assignment 2
 * --------------------------
 * File name: GameContoller.java
 * Author: V Prathyaksha
 * Student Number: 5283048
 * Description: Plays one round of the game, 
 * includes miscellaneous functions required
 * for that one round
 */
import java.util.*;

public class GameController {

	private GameView view;
	private Dealer dealer;
	private ArrayList<GamePlayerInterface> players;
	static int betOnTable = 0;
	int result = 0;

	public GameController(GameView view, Dealer dealer,
			ArrayList<GamePlayerInterface> players) {
		this.view = view;
		this.dealer = dealer;
		this.players = players;
	}
// play game for one round
	public int run() {
		// start game
		// dealers shuffle cards
		view.displayIntro();
		view.printLine();
		
		dealer.shuffleCards();
		view.printLine();
		
		int numOfCards = 0;
		int numOfRounds = 1;
		
		while(numOfRounds < 5){
			// Dealer dealing cards to players
			view.displayRound(numOfRounds);
			view.printLine();
			numOfRounds++;
			// First round --> face down for each player
			if(numOfCards == 0){
				for (GamePlayerInterface player : players) {
					// take out card from dealer and give it to player
					Card card = dealer.dealCard();
					player.addCard(card);
					numOfCards++;
					// NEED to add total and both players for current record
					view.updateTable(players,player);
					view.printLine();
				}
			}
			
			int currentPDvalue[] = new int[2];
			String currentPDname[] = new String[2];
			String currentPDsuit[] = new String[2];
			int x = 0;
			// Second card onwards
				for (GamePlayerInterface player : players) {
					// take out card from dealer and give it to player
					Card card = dealer.dealCard();
					player.addCard(card);
					
					currentPDvalue[x] = card.getValue();
					currentPDname[x] = card.getName();
					currentPDsuit[x] = card.getSuit();
					x++;
					
					numOfCards++;
					
					view.updateTable(players,player);
					view.printLine();
				}
				
				// Check which card is higher
				
				int PDbig = 0;
				PDbig = compareBigger(currentPDvalue[0], currentPDvalue[1], currentPDsuit[0], 
										currentPDsuit[1], currentPDname[0],currentPDname[1]);
				
				// Do any betting by either Player or Dealer
				boolean ifContinue = true;
				ifContinue = displayBet(PDbig, players);
				if(!ifContinue){
					result = 1;
					break;
				}
				
				view.printLine();
		}
		
		//show all player's cards (include hidden cards
		view.updateTableShowAllCards(players);
		//assume human player is always the first in the arraylist
		GamePlayerInterface player = players.get(0);
		
		//determine if human player has win
		view.printLine();
		view.print("Determine winning");
		
		result = determineWinning(player, dealer);
		int totalChips = players.get(0).getChips();
		
		switch(result){
		case 0: totalChips = (players.get(0).getChips()) + (betOnTable/2);
				players.get(0).setChips(totalChips);
				return totalChips;
		case 1: break;
		case 2:	totalChips = (players.get(0).getChips()) + betOnTable;
				players.get(0).setChips(totalChips);
				return totalChips;
		default: view.print("Only 3 possibilities. Contact admin for assistance!");
		}
		view.printChips(totalChips);
		view.print("End of Game");
		//end of one game
		
		// Remove cards from previous arrays and re-add to original deck
		for(GamePlayerInterface p : players){
			recreateDeck(p);
		}
		
		// initially, return result
		return totalChips;
	}
	// return 1 Dealer win, 2 for Player win, else for Draw
	private int determineWinning(GamePlayerInterface player, GamePlayerInterface dealer)
    {
        
         int playerValue = player.getTotalCardsValue();
         int dealerValue = dealer.getTotalCardsValue();
         int result = 0;
         
         if(dealerValue > playerValue)
         {
        	 view.print("Dealer Wins");
        	 view.printLine();
        	 result = 1;
         }
         
         else if(dealerValue < playerValue)
         {
        	 view.print("Player Wins");
        	 view.printLine();
        	 result = 2;
         }
         
         else{
        	 view.print("Match Draw!");
        	 view.printLine();
         }
         
         return result;
    }	
	public int compareBigger(int pValue, int dValue, String pSuit, 
								String dSuit, String pName, String dName){
		int num = 0;
		
		// To check P and D value
		if(pValue > dValue){
			num = 0;
		}
		else if(pValue < dValue){
			num = 1;
		}
		
		// To check P and D suit (value is equal)
		if (getSuitValue(pSuit) > getSuitValue(dSuit)){
			num = 0;
		}
		else if (getSuitValue(pSuit) < getSuitValue(dSuit)){
			num = 1;
		}
		
		// To check P and D name (value and suit are equal)
		if (getNameValue(pName) > getNameValue(dName)){
			num = 0;
		}
		else if (getNameValue(pName) < getNameValue(dName)){
			num = 1;
		}
		return num;
	}
	public int getSuitValue(String pdSuit){	
		int pxValue = 0;
		switch(pdSuit){	
			case "Spade":	pxValue = 4;
			case "Heart":	pxValue = 3;
			case "Club":	pxValue = 2;
			case "Diamond":	pxValue = 1;
			default:	pxValue = 0;
		}
		return pxValue;	
	}	
	public int getNameValue(String pdName){	
		int pxName = 0;
		switch(pdName){	
			case "10":	pxName = 4;
			case "J":	pxName = 3;
			case "Q":	pxName = 2;
			case "K":	pxName = 1;
			default:	pxName = 0;
		}
		return pxName;	
	}	
	public boolean displayBet(int PDbig, ArrayList<GamePlayerInterface> players){
		boolean isContinue = true;
		char decision;
		int currentBet = 0;
		int max = players.get(0).getChips();
		
		view.print("Maximum number of chips allowed to bet is: " + max);
		
		if(PDbig == 1){
			Random r = new Random();
			currentBet = r.nextInt(max);
			
			view.print("Dealer call, state bet: " + currentBet);
			
			String toContinue = "Do you want to follow? [Y/N]: ";
			decision = Keyboard.readChar(toContinue);
			if(Character.toUpperCase(decision) == 'Y'){
				view.print("Player chooses to Follow");
				view.printLine();
			}
			else{
				view.print("Player chooses not to Follow");
				isContinue = false;
			}
		}
		
		else{
			String toContinue = "Do you want to [C]all or [Q]uit?: ";
			decision = Keyboard.readChar(toContinue);
			if(Character.toUpperCase(decision) == 'C'){
				view.print("Player chooses to Call");
				String prompt = "Player call, state bet: ";
				currentBet = Keyboard.readInt(prompt);
				view.print("Player places his bet " + currentBet);
			}
			else{
				view.print("Player chooses to Quit");
				isContinue = false;
			}
		}
		
		betOnTable = betOnTable + (2 * currentBet);
		view.print("Current bet on table: " + betOnTable);
		
		players.get(0).deductChips(currentBet);
		players.get(1).deductChips(currentBet);
		int playerGetChips = players.get(0).getChips();
		view.displayGetChips(playerGetChips);
		if(players.get(0).getChips() < 0){
			view.print("Insufficient chips");
			players.get(0).setChips(0);
			isContinue = false;
		}
		
		view.printLine();
		view.print(players.get(0).getLoginName() + ", you are left with " + players.get(0).getChips());
		
		return isContinue;
	}	
	public void recreateDeck(GamePlayerInterface player){
		for(int i = 0; i < player.getNumberOfCardsOnHand(); i++){
			dealer.deck.appendCard(player.getCardsOnHand().get(i));
		}
		player.clearCardsOnHand();
	}
}
