public class GameServer {

	private GameServerViewerThread viewer; 
	private GameServerPlayerThread player;
	
	public GameServer() {}

	AdminData admindata = new AdminData();
	
	public void run() {
		
		// Admin login
		String adminUsername, adminPassword;
		boolean isAccount = false;
		
		do{
			adminUsername = Keyboard.readString("Enter Login name: ");
			adminPassword = Keyboard.readString("Enter Password: ");
			
			isAccount = admindata.checkAccount(adminUsername, adminPassword);
			
		}while(isAccount);
		
		BroadCaster broadCaster = new BroadCaster();
		
		//A thread to let viewers connect in
		viewer = new GameServerViewerThread(broadCaster);
		viewer.start();
		
		//A thread to let player connect in
		player = new GameServerPlayerThread(broadCaster,this);
		player.start();		
	}
	
	
	public void endGameServerViewerThread(){
		viewer.end();
	}
	

	public static void main(String[] args) {
		new GameServer().run();
	}

}