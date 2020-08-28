/*
 * CSCI213 Assignment 3
 * --------------------------
 * File name: NewGameListener.java
 * Author: V Prathyaksha
 * Student Number: 5283048
 * Description: Action Listener for player
 * to decide if he wants to play another game
 * or not
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

public class NewGameListener implements ActionListener{
	JPanel gaming = new JPanel();
	JLabel question = new JLabel("Next Game? (Y/N) > ");
	JTextField answer = new JTextField("", 3);
	JButton submit = new JButton("Enter");
	String ans;
	
	Font customFont = new Font("Serif", Font.PLAIN, 32);
	
	public NewGameListener() {
		
		gaming.setBackground(Color.pink);
		
		question.setFont(customFont);
		answer.setFont(customFont);
		submit.setFont(customFont);
		
		gaming.add(question);
		gaming.add(answer);
		gaming.add(submit);
		
		answer.addActionListener(this);
		submit.addActionListener(this);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		ans = answer.getText();
	}

	public String getAns() {
		return ans;
	}
	public void setAns(String ans) {
		this.ans = ans;
	}

}
