/*
 * CSCI213 Assignment 3
 * --------------------------
 * File name: BetListener.java
 * Author: V Prathyaksha
 * Student Number: 5283048
 * Description: Action Listener for player 
 * to enter amount to bet
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

public class BetListener implements ActionListener{
	JPanel betting = new JPanel();
	JLabel question = new JLabel("Player call, state bet: ");
	JTextField answer = new JTextField("", 5);
	JButton submit = new JButton("Enter");
	String ans;
	int betAmount;
	
	Font customFont = new Font("Serif", Font.PLAIN, 32);
	
	public BetListener() {
		
		betting.setBackground(Color.pink);
		
		question.setFont(customFont);
		answer.setFont(customFont);
		submit.setFont(customFont);
		
		betting.add(question);
		betting.add(answer);
		betting.add(submit);
		
		answer.addActionListener(this);
		submit.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent event) {
		ans = answer.getText();	
		betAmount = Integer.parseInt(ans);
	}
	public int getBetAmount() {
		return betAmount;
	}
	public void setBetAmount(int betAmount) {
		this.betAmount = betAmount;
	}
}
