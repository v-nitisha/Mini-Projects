/*
 * CSCI213 Assignment 3
 * --------------------------
 * File name: GameModule.java
 * Author: V Prathyaksha
 * Student Number: 5283048
 * Description: Does login and setting of player
 * before start of game
 */

import java.util.*;
import javax.swing.*;

//@SuppressWarnings("serial")
public class GameModule extends JPanel{

	JPanel pnl = new JPanel();
	JLabel gameLabel;
	static char chGame;
	
	public void run(){
		String playerUsername;
		

		ViewTransmitter tx = new ViewTransmitter();
		GameView view = new GameView(tx);
		
		PlayersData playerdata = new PlayersData();
		
		PlayerLoginListener login = new PlayerLoginListener();
		try {
			do{
				Thread.sleep(1000);
			}while(login.getPlayerUser() == null && login.getPlayerPass() == null);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		playerUsername = login.getPlayerUser();
		
		int i = 0;
		i = playerdata.checkWhichNumber(playerUsername);
		
		// Create the human player
		GamePlayer humanPlayer = new GamePlayer(playerdata.playerList.get(i).getLoginName(), playerdata.playerList.get(i).getHashedPassword(), 
												playerdata.playerList.get(i).getChips(), playerdata.playerList.get(i).getName(),
												playerdata.playerList.get(i).getEmail(), playerdata.playerList.get(i).getBirthdate());
		
		BrainInterface humanBrain = new HumanBrain();
		humanPlayer.setBrain(humanBrain);
		
		BrainInterface dealerBrain = new GotBrain();	

		//create dealer
		Dealer dealer = new Dealer();
		dealer.setBrain(dealerBrain);
		
		ArrayList<GamePlayerInterface> players = new ArrayList<GamePlayerInterface>();
		players.add(humanPlayer);
		players.add(dealer);	
		
		//this will run one game
		GameController controller = new GameController(view,dealer,players);
		
		//return 1 for draw 2 for loss 3 for win
		do{
	        view.playerPanel.removeAll();
	        view.playerPanel.revalidate();
	        view.playerPanel.repaint();
	        
	        view.dealerPanel.removeAll();
	        view.dealerPanel.revalidate();
	        view.dealerPanel.repaint();
	        
			int result = controller.run();
			
			view.infoPanel.revalidate();
			view.infoPanel.repaint();
			
			// Write into file
			playerdata.playerList.get(i).setChips(result);
			playerdata.updatePlayerDataToFile();
						
			String toGame = "";
            NewGameListener game = new NewGameListener();
			controller.view.infoPanel.add(game.gaming);
			try{
                do{
                    Thread.sleep(1000);
                }while(game.getAns() == null);
			
			} catch (InterruptedException e) {
			e.printStackTrace();
			}
		toGame = game.getAns();
		System.out.println(toGame);
		chGame = toGame.charAt(0);
		System.out.println(chGame);
		
		view.infoPanel.remove(game.gaming);
        view.infoPanel.revalidate();
        view.infoPanel.repaint();
        
		controller.mainPanel.removeAll();
		controller.mainPanel.revalidate();
		controller.mainPanel.repaint();
        
		controller.loopPanel.removeAll();
		controller.loopPanel.revalidate();
		controller.loopPanel.repaint();
        
		}while((chGame == 'Y') || (chGame == 'y'));
	}
}