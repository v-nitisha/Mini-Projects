import java.io.*;
import java.net.*;

public class GameServerPlayerThread extends Thread {

	private static final int DEFAULT_PORT = 4445;
	private BroadCaster broadcaster;
	private ServerSocket reception_socket;
	private Socket client_socket;
	private GameServer gameServer;
	
	public GameServerPlayerThread(BroadCaster broadcaster, GameServer gameServer) {
		this.broadcaster = broadcaster;
		this.gameServer = gameServer; 
	}

	public void run() {
		int port = DEFAULT_PORT;

		try {
			reception_socket = new ServerSocket(port);
			System.out.println("Started server on port:" + port);
		} catch (IOException io) {
			System.out.println("Cannot create server socket");
			System.exit(0);
		}

		// only take in one player socket
		try {
			client_socket = reception_socket.accept();
			handleClient(client_socket);
		} catch (IOException ex) {
			System.out.println("Problem accepting client socket");
		}

		handleClient(client_socket);

	}

	private void handleClient(Socket sock) {

		// Get input and output stream
		ObjectInputStream requests = null;
		//ObjectOutputStream responses = null;
		try {
			//responses = new ObjectOutputStream(sock.getOutputStream());
			//responses.flush();
			requests = new ObjectInputStream(sock.getInputStream());
		} catch (IOException io) {
			System.out.println("Cannot open stream");
			try {
				sock.close();
			} catch (Exception e) {
			}
			return;
		}

		boolean state = true;
		while (state) {

			try {

				Message message = (Message) requests.readObject();
				if (message.getControl().equals("Message")) {
					//sent player message to all viewers
					broadcaster.newMessage(message);
				}else if(message.getControl().equals("Quit")){
					
					state = false;
				}

			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
		
		end();
		System.out.println("GameServerPlayerThread ends");

	}

	
	private void end(){

		gameServer.endGameServerViewerThread();
		
		try {
			reception_socket.close();
			client_socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
	
}