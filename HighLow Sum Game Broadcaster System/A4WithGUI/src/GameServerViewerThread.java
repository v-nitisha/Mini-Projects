import java.io.*;
import java.net.*;

public class GameServerViewerThread extends Thread {

	private static final int DEFAULT_PORT = 4444;
	private BroadCaster broadcaster;
	private boolean state;
	private ServerSocket reception_socket;
    private int totalclients = 0;
	
	public GameServerViewerThread(BroadCaster broadCaster) {
		this.broadcaster = broadCaster;
		state = true;
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

		while(state) {
			Socket client_socket = null;

			try {
				client_socket = reception_socket.accept();
                totalclients += 1;
                System.out.println(client_socket.toString());
                System.out.println("Total Viewers : "+totalclients);
                new Thread(new GameViewerTransmitter(client_socket)).start();
				//GameViewerTransmitter new GameViewerTransmitter(client_socket);
				//broadcaster.addClient(client);
			}catch(SocketException se){
				//closing the reception_socket will cause this exception to be thrown
				state = false;
				broadcaster.removeAllClient();
			} catch (IOException ex) {
				System.out.println("Problem accepting client socket");
			}
		}
		end();
		System.out.println("GameServerViewerThread ends");	
	}

	public void end(){
		try {
			reception_socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}