/*
 * CSCI213 Assignment 3
 * --------------------------
 * File name: PlayerLoginListener.java
 * Author: V Prathyaksha
 * Student Number: 5283048
 * Description: Action Listener for player 
 * to login and check password
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class PlayerLoginListener extends JFrame implements ActionListener{
	JPanel pnl = new JPanel();
	JTextField Username = new JTextField("", 10);
	JTextField Password = new JTextField("", 10);
	JButton submit = new JButton("Submit");
	JLabel lbl1 = new JLabel("Enter Login name: ");
	JLabel lbl2 = new JLabel("Enter Password: ");

	Font customFont = new Font("Serif", Font.PLAIN, 32);

	PlayersData playerData = new PlayersData();

	boolean isAccount = false;
	String playerUser, playerPass;

	public PlayerLoginListener(){
		super("PlayerLoginListener");

		pnl.setBackground(Color.pink);

		add(pnl);

		lbl1.setFont(customFont);
		lbl2.setFont(customFont);

		pnl.add(lbl1);
		pnl.add(Username);
		pnl.add(lbl2);
		pnl.add(Password);
		pnl.add(submit);

		Username.addActionListener(this);
		Password.addActionListener(this);
		submit.addActionListener(this);

		setVisible(true);
		pack();
	}
	@Override
	public void actionPerformed(ActionEvent event) {

		playerUser = Username.getText();
		playerPass = Password.getText();

		setPlayerUser(playerUser);
		setPlayerPass(playerPass);

		isAccount = playerData.checkAccount(playerUser, playerPass);
		System.out.println(isAccount);
		if(isAccount == false){
			playerData.loginUnsuccessful();
		}
		if(isAccount == true){
			playerData.loginSuccessful();
		}
	}
	public String getPlayerUser() {
		return playerUser;
	}
	public void setPlayerUser(String playerUser) {
		this.playerUser = playerUser;
	}
	public String getPlayerPass() {
		return playerPass;
	}
	public void setPlayerPass(String playerPass) {
		this.playerPass = playerPass;
	}
}
