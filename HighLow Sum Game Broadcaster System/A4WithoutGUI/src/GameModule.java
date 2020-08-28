/*
 * CSCI213 Assignment 2
 * --------------------------
 * File name: GameModule.java
 * Author: V Prathyaksha
 * Student Number: 5283048
 * Description: Does login and setting of player
 * before start of game
 */

 import java.util.*;

public class GameModule {
	public void run(){
		ViewTransmitter tx = new ViewTransmitter();
		GameView view = new GameView(tx);
		MainPlayersData playerdata = new MainPlayersData();
		
		view.printMessage("HIGHLOW SUM GAME LOGIN");

			//Player login
			String playerUsername, playerPassword;
			
			playerUsername = Keyboard.readString("Enter Login name: ");
			playerPassword = Keyboard.readString("Enter Password: ");
			
			int i = 0;
			
			boolean isAccount = false;
			isAccount = playerdata.checkAccount(playerUsername, playerPassword);
			if(isAccount){
				view.printMessage("There is error in login! Please try again.");
			}
		
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
		
		view.printLine();
		view.printMessage("HIGHLOW SUM GAME");
		
		char gameNext;
		do{
			view.printLine();
			int result = controller.run();
			
			String nextG = "Next Game? (Y/N) > ";
			gameNext = Keyboard.readChar(nextG);
			
			// Write into file
			playerdata.playerList.get(i).setChips(result);
			playerdata.updateMainPlayersDataToFile();
			
			view.printLine();
		}while(Character.toUpperCase(gameNext) == 'Y');
			
	}
}
