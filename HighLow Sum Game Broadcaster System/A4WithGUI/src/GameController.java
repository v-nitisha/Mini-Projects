/*
 * CSCI213 Assignment 3
 * --------------------------
 * File name: GameContoller.java
 * Author: V Prathyaksha
 * Student Number: 5283048
 * Description: Plays one round of the game, 
 * includes miscellaneous functions required
 * for that one round
 */
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class GameController{
	GameView view;
	private Dealer dealer;
	private ArrayList<GamePlayerInterface> players;
	static int betOnTable = 0;
	int result = 0;
	int DELAY = 60;
	int counter = 0;
	
	Font customFont = new Font("Serif", Font.BOLD, 40);
	Font labelFont = new Font("Serif", Font.PLAIN, 35);
	Font generalFont = new Font("Serif", Font.PLAIN, 25);
	Font roundFont = new Font("Serif", Font.BOLD, 25);
	Font lineFont = new Font("Serif", Font.PLAIN, 30);
	
	JPanel playerPanel = new JPanel();
	JPanel mainPanel = new JPanel();
	JPanel loopPanel = new JPanel();
	
	BoxLayout boxPlayer = new BoxLayout(playerPanel, BoxLayout.Y_AXIS);
	BoxLayout boxLoop = new BoxLayout(loopPanel, BoxLayout.Y_AXIS);
	
	String blank = "                    ";
	String line = "-----------------------------------------------------------------------------";
	
	public GameController(GameView view, Dealer dealer,
			ArrayList<GamePlayerInterface> players) {
		this.view = view;
		this.dealer = dealer;
		this.players = players;
	}
	
	// play game for one round
	public int run() {
		
		/*
		// ScrollBar for loopPanel only
		JScrollBar loopPane = new JScrollBar(JScrollBar.VERTICAL,0,40,0,100);
		int height = loopPane.getPreferredSize().height;
		loopPane.setPreferredSize(new Dimension(30, height));
		// ScrollBar ends here
		*/
		
		playerPanel.setLayout(boxPlayer);
		loopPanel.setLayout(boxLoop);
		
		mainPanel.setBackground(Color.lightGray);
		
		// Start game AND dealers shuffle cards
		JLabel start = new JLabel(blank + "WELCOME TO HIGH SUM GAME:- START");
		start.setFont(customFont);
		
		view.add(mainPanel);
		
		BoxLayout mainBox = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(mainBox);
		mainPanel.setPreferredSize(new Dimension(2500,100));
		loopPanel.setPreferredSize(new Dimension(300,200));

		mainPanel.add(start);
		
		view.contentPane.add("North", mainPanel);
		dealer.shuffleCards();
		
		int numOfCards = 0;
		int numOfRounds = 1;
		
		JLabel lineLabel = new JLabel(line);
		lineLabel.setFont(lineFont);
		
		while(numOfRounds < 5){
			loopPanel.add(lineLabel);
			
			// Dealer dealing cards to players
			JLabel dealing = new JLabel("Dealer dealing cards - ROUND " + numOfRounds);
			dealing.setFont(roundFont);
			loopPanel.add(dealing);
			
			numOfRounds++;
			
			// First round --> face down for each player
			if(numOfCards == 0){
				
				for (GamePlayerInterface player : players) {
					// take out card from dealer and give it to player
					Card card = dealer.dealCard();
					player.addCard(card);
					numOfCards++;
				}

				view.contentPane.add("Center", loopPanel);
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
			}
			
			for(GamePlayerInterface player : players){
				view.showPlayerCards(player);
				JLabel lineLabel2 = new JLabel(line);
				
				if(player == players.get(0)){

					view.playerPanel.add(lineLabel2);
				}else{
					view.dealerPanel.add(lineLabel2);
				}
			}
			
			view.revalidate();
			view.repaint();
			
			// Check which card is higher
			int PDbig = 0;
			PDbig = compareBigger(currentPDvalue[0], currentPDvalue[1], currentPDsuit[0], 
									currentPDsuit[1], currentPDname[0],currentPDname[1]);
			
            view.revalidate();
			view.repaint();
			
			// Do any betting by either Player or Dealer
			boolean ifContinue = true;
			ifContinue = displayBet(PDbig, players);
			
			view.revalidate();
			view.repaint();
			
			if(!ifContinue){
				result = 1;
				break;
			}
		}
		
		//assume human player is always the first in the arraylist
		GamePlayerInterface player = players.get(0);
				
		loopPanel.add(lineLabel);
		
		JLabel noGame = new JLabel("Only 3 possibilities. Contact admin for assistance!");
		noGame.setFont(generalFont);
		
		// Determines who the winner is
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
		default: loopPanel.add(noGame);
		}

		JLabel playerTotal = new JLabel("Total chips for Player is: " + totalChips);
		playerTotal.setFont(generalFont);
		loopPanel.add(playerTotal);
		
		JLabel EoG = new JLabel("End of Game");
		EoG.setFont(customFont);
		loopPanel.add(EoG);
		//end of one game
		
		//show dealer's hidden card
		view.showAllPlayerCard(players.get(1), loopPanel);
		
		// Remove cards from previous arrays and re-add to original deck
		for(GamePlayerInterface p : players){
			recreateDeck(p);
		}
        
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
		view.setVisible(true);
        
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
        	 JLabel dealerWin = new JLabel("Dealer Wins");
     		 dealerWin.setFont(generalFont);
     		 loopPanel.add(dealerWin);
        	 result = 1;
         }
         
         else if(dealerValue < playerValue)
         {
        	 JLabel playerWin = new JLabel("Player Wins");
     		 playerWin.setFont(generalFont);
     		 loopPanel.add(playerWin);
        	 result = 2;
         }
         
         else{
        	 JLabel matchDraw = new JLabel("Match Draw!");
        	 matchDraw.setFont(generalFont);
     		 loopPanel.add(matchDraw);
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
		if(num == 0){
			JLabel message = new JLabel("Player value is bigger");
			message.setFont(generalFont);
			loopPanel.add(message);
		}
		else if(num == 1){
			JLabel message = new JLabel("Dealer value is bigger");
			message.setFont(generalFont);
			loopPanel.add(message);
		}
		else{
			System.out.println("");
		}
		view.revalidate();
		view.repaint();
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
		
		loopPanel.revalidate();
		loopPanel.repaint();
		
		boolean isContinue = true;
		int currentBet = 0;
		
		int max = players.get(0).getChips();
		if(max < 0){
			isContinue = false;
		}
		
		JLabel maxChipsMessage = new JLabel("Maximum number of chips allowed to bet is: " + max);
		maxChipsMessage.setFont(generalFont);
		loopPanel.add(maxChipsMessage);
		
		if(PDbig == 1){
			Random r = new Random();
			currentBet = r.nextInt(max);
			
			if(currentBet <= 0){
				isContinue = false;
			}
			
			String stateBet = "Dealer call, state bet: " + currentBet;
			JLabel dealerCallStateBet = new JLabel(stateBet);
			dealerCallStateBet.setFont(generalFont);
			view.infoPanel.revalidate();
			view.infoPanel.repaint();
			loopPanel.add(dealerCallStateBet);
			
			String toFollow = "";
            FollowListener follow = new FollowListener();
            view.infoPanel.revalidate();
			view.infoPanel.repaint();
			view.infoPanel.add(follow.following);
			try {
                    do{
                        Thread.sleep(1000);
                    }while(follow.getAns() == null);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			toFollow = follow.getAns();
			char chFollow = toFollow.charAt(0);
			
			
			if((chFollow == 'Y') || (chFollow == 'y')){
			}
			else{
				isContinue = false;
			}

			view.infoPanel.remove(follow.following);
            view.infoPanel.revalidate();
            view.infoPanel.repaint();
		}
		
		else{
			String toContinue = "";
			PromptListener prompt = new PromptListener();
			view.infoPanel.revalidate();
			view.infoPanel.repaint();
			view.infoPanel.add(prompt.prompting);
            view.infoPanel.revalidate();
			view.infoPanel.repaint();
			try {
	                do{
	                    Thread.sleep(1000);
	                }while(prompt.getAns() == null);
			
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			toContinue = prompt.getAns();
			char chContinue = toContinue.charAt(0);

			if((chContinue == 'C') || (chContinue == 'c')){
				view.infoPanel.revalidate();
				view.infoPanel.repaint();
				
				BetListener bet = new BetListener();


				view.infoPanel.revalidate();
				view.infoPanel.repaint();
				
				view.infoPanel.add(bet.betting);

				try {
					do{
	                    Thread.sleep(500);
                    }while(bet.getBetAmount() == 0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				currentBet = bet.getBetAmount();
			}
			else{
				isContinue = false;
			}
		}
		
		view.infoPanel.removeAll();
        view.infoPanel.revalidate();
        view.infoPanel.repaint();

		
		betOnTable = betOnTable + (2 * currentBet);
		JLabel tableBet = new JLabel("Current bet on table: " + betOnTable);
		tableBet.setFont(generalFont);
		loopPanel.add(tableBet);
		
		players.get(0).deductChips(currentBet);
		players.get(1).deductChips(currentBet);
		
		if(players.get(0).getChips() < 0){	
			
			JLabel notEnough = new JLabel("Insufficient chips");
			notEnough.setFont(generalFont);
			loopPanel.add(notEnough);
			
			players.get(0).setChips(0);
			isContinue = false;
		}
		
		String leftWith = players.get(0).getLoginName() + "'s chips left: " + players.get(0).getChips();
		JLabel notEnough = new JLabel(leftWith);
		notEnough.setFont(generalFont);
		loopPanel.add(notEnough);

		return isContinue;
	}	
	public void recreateDeck(GamePlayerInterface player){
		for(int i = 0; i < player.getNumberOfCardsOnHand(); i++){
			dealer.deck.appendCard(player.getCardsOnHand().get(i));
		}
		player.clearCardsOnHand();
	}
}
