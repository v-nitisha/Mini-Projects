/*
 * CSCI213 Assignment 3
 * --------------------------
 * File name: PomptListener.java
 * Author: V Prathyaksha
 * Student Number: 5283048
 * Description: Action Listener for player
 * to either Call or Quit
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

public class PromptListener implements ActionListener{
	JPanel prompting = new JPanel();
	JLabel question = new JLabel("Do you want to [C]all or [Q]uit?: ");
	JTextField answer = new JTextField("", 3);
	JButton submit = new JButton("Enter");
	String ans;
	
	Font customFont = new Font("Serif", Font.PLAIN, 32);
	
	public PromptListener() {
		
		prompting.setBackground(Color.pink);
		
		question.setFont(customFont);
		answer.setFont(customFont);
		submit.setFont(customFont);
		
		prompting.add(question);
		prompting.add(answer);
		prompting.add(submit);
		
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
