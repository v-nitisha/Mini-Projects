/*
 * CSCI213 Assignment 2
 * --------------------------
 * File name: GameView.java
 * Author: V Prathyaksha
 * Student Number: 5283048
 * Description: Does all display related functions
 */
import java.util.*;
import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameView extends JFrame{
	
	private ViewTransmitter tx;
	
	Deck deck = new Deck();
	JPanel cardPanel = new JPanel();
	Font customFont = new Font("Serif", Font.PLAIN, 32);
	Font dpFont = new Font("Serif", Font.PLAIN, 27);
	FlowLayout flow = new FlowLayout();
	JPanel titleDealerPanel = new JPanel();
	JPanel titlePlayerPanel = new JPanel();
	
	Container contentPane = getContentPane();
	
	JPanel dealerPanel = new JPanel();
	JPanel playerPanel = new JPanel();
	JPanel infoPanel = new JPanel();
	
	public GameView(ViewTransmitter tx){
		this.tx = tx;
		
		JLabel dealerLabel = new JLabel("Dealer");
		dealerLabel.setFont(dpFont);
		dealerLabel.setBorder(BorderFactory.createEmptyBorder(10,50,10,50));
		dealerPanel.setBorder(BorderFactory.createEmptyBorder(20,30,30,30));
		dealerPanel.add(dealerLabel);

		JLabel playerLabel = new JLabel("Player");
		playerLabel.setBorder(BorderFactory.createEmptyBorder(10,50,10,50));
		playerPanel.setBorder(BorderFactory.createEmptyBorder(20,30,30,30));
		playerLabel.setFont(dpFont);
		playerPanel.add(playerLabel);
		
		// Set size of overall frame
		setPreferredSize(new Dimension(1300,1365));
		
		infoPanel.setBackground(Color.lightGray);
		dealerPanel.setBackground(Color.pink);
		playerPanel.setBackground(Color.pink);
		
		infoPanel.setPreferredSize(new Dimension(2500,100));
		dealerPanel.setPreferredSize(new Dimension(250,700));
		playerPanel.setPreferredSize(new Dimension(250,700));
		
		contentPane.add("South", infoPanel);
		contentPane.add("West", dealerPanel);
		contentPane.add("East", playerPanel);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		pack();
		
		
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

	// This will show the hidden card only
	public void showAllPlayerCard(GamePlayerInterface p, JPanel loopPanel){
		ArrayList<Card> cardsList = p.getCardsOnHand();
		JLabel hidden = new JLabel("Dealer's Hidden Card");
		hidden.setFont(dpFont);
		loopPanel.add(hidden);
		loopPanel.add(deck.showCard(cardsList.get(0)));
		loopPanel.revalidate();
		loopPanel.repaint();
	}
	
	// This will show all cards, but hidden will remain hidden
	public void showPlayerCards(GamePlayerInterface player){
		ArrayList<Card> cards = player.getCardsOnHand();
		int count = 0;
		for(Card card:cards){
			if(!player.isHuman() && count == 0){
				titleDealerPanel = deck.showCard(card, "Hidden");
				dealerPanel.add(titleDealerPanel);
				count++;
			}
			else{
				if(!player.isHuman()){
					titleDealerPanel = deck.showCard(card);
					dealerPanel.add(titleDealerPanel);
				}
				else{
					titlePlayerPanel = deck.showCard(card);
					playerPanel.add(titlePlayerPanel);
				}
			}
		}
		
		if(player.isHuman()){
			String dealingTotal = "Player's current total: " + player.getTotalCardsValue();
			JLabel dealing = new JLabel(dealingTotal);
			dealing.setFont(customFont);
			// infoPanel.add(dealing);
		}
		
		cardPanel.add(deck.subPanelMain);
		pack();
		setVisible(true);
		
	}
}
