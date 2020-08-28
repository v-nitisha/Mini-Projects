
import java.util.ArrayList;

public class BroadCaster {

	private ArrayList<GameViewerTransmitter> clients;
	
	public BroadCaster(){
		clients = new ArrayList<GameViewerTransmitter>();
	}
	public synchronized void newMessage(Message message){

		for(GameViewerTransmitter client: clients){
			client.sentMessage(message);
		}
	}
	
	public synchronized void removeAllClient(){
		
		//close all the connections
		for(GameViewerTransmitter client: clients){
			client.end();
		}
		
		clients.clear();
	}
	public synchronized void addClient(GameViewerTransmitter client){
		clients.add(client);
	}
	
	
}
