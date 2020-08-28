/*
 * CSCI213 Assignment 3
 * --------------------------
 * File name: FollowListener.java
 * Author: V Prathyaksha
 * Student Number: 5283048
 * Description: Action Listener for player
 * to decide if he wants to follow or not
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

public class FollowListener implements ActionListener{
	JLabel question = new JLabel("Do you want to follow? [Y/N]: ");
	JTextField answer = new JTextField("", 3);
	JButton submit = new JButton("Enter");
	String ans;
	JPanel following = new JPanel();
	
	Font customFont = new Font("Serif", Font.PLAIN, 32);
	
	public FollowListener() {
		
		following.setBackground(Color.pink);
		
		question.setFont(customFont);
		answer.setFont(customFont);
		submit.setFont(customFont);
		
		following.add(question);
		following.add(answer);
		following.add(submit);
		
		answer.addActionListener(this);
		submit.addActionListener(this);
	}



	@Override
	public void actionPerformed(ActionEvent event) {
		
		ans = answer.getText();
		setAns(ans);
	}

	public String getAns() {
		return ans;
	}
	public void setAns(String ans) {
		this.ans = ans;
	}

}
