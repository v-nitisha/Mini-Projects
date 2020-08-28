/*
 * CSCI213 Assignment 3
 * --------------------------
 * File name: Deck.java
 * Author: V Prathyaksha
 * Student Number: 5283048
 * Description: Displays card image etc.
 */

import java.awt.FlowLayout;
import java.awt.Font;
import java.util.*;
import javax.swing.*;

public class Deck{
    private ArrayList<Card> cards;
	JPanel subPanel1, subPanel2;
	JPanel subPanelMain = new JPanel();
	JPanel imagePanel, titlePanel;
	Font customFont = new Font("Serif", Font.PLAIN, 32);
	FlowLayout flow = new FlowLayout();
	BoxLayout box1 = new BoxLayout(subPanel1, BoxLayout.Y_AXIS);
	BoxLayout box2 = new BoxLayout(subPanel2, BoxLayout.Y_AXIS);
    
    public Deck()
    {		
        cards = new ArrayList<Card>();
        String [] suits = {"Heart","Diamond","Spade","Club"};
        
        int jqkValue = 0;
        
        for(int n=0;n<suits.length;n++)
        {
            String suit = suits[n];
            Card card;
            
            card = new Card(suit,"Ace",1);
            cards.add(card);
            
            jqkValue = 1;
            if(suit == "Club" | suit == "Spade"){
            	jqkValue = -1;
            }
            
            for(int i=2;i<=10;i++)
            {
                card = new Card(suit,i+"",(i*jqkValue));
                cards.add(card);
            }

            jqkValue = 10;
            if(suit == "Club" | suit == "Spade"){
            	jqkValue = -10;
            }
            card = new Card(suit,"Jack",jqkValue);
            cards.add(card);
         
            card = new Card(suit,"Queen",jqkValue);
            cards.add(card);
      
            card = new Card(suit,"King",jqkValue);
            cards.add(card);
        }
        
    }
    
    public void showCards()
    {   
    	for(Card card: cards){
    		showCard(card);
       }
    }
    
    public JPanel showCard(Card card){
    	subPanel1 = new JPanel();
    	subPanel1.setSize(80, 80);
    	
    	String imageName = card.getSuit() + card.getName() + ".jpg";
  	    ImageIcon cardImage = new ImageIcon(imageName);
  	   
    	 imagePanel = new JPanel();
    	 JLabel img = new JLabel(cardImage);
    	 imagePanel.add(img);
    	 
    	 return imagePanel;
    }
    
    public JPanel showCard(Card card, String name){
    	subPanel2 = new JPanel();
    	subPanel2.setSize(80, 80);

  	    String imageName = card.getSuit() + "Hidden" + ".jpg";
  	    ImageIcon cardImage = new ImageIcon(imageName);
  	   
	  	 JPanel imagePanel = new JPanel();
		 JLabel img = new JLabel(cardImage);
		 imagePanel.add(img);
		 
		 return imagePanel;
     }
    
    public Card dealCard()
    {
        return cards.remove(0);
    }
    
    public void appendCard(Card c)
    {
        cards.add(c);
        
    }
    
    public void appendCards(ArrayList<Card> cards)
    {  
    	shuffle(cards);
        for(Card c: cards)
            this.cards.add(c);
        
    }
    
    public void shuffle(){
    	shuffle(cards);
    }
    
    public void shuffle(ArrayList<Card> cards)
    {
        Random r = new Random();
        for(int i=0;i<cards.size()*1000;i++)
        {
            int a = r.nextInt(cards.size());
            int b = r.nextInt(cards.size());
            
            Card cardA = cards.get(a);
            Card cardB = cards.get(b);
            
            cards.set(a, cardB);
            cards.set(b, cardA);
            
        }
        
    }
}
