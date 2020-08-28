
import java.net.*;
import java.io.*;

public class GameViewer {

	private static final int DEFAULT_PORT = 4444;
	private static final String hostName = "localhost";

	private ObjectInputStream responseStream;
	private ObjectOutputStream requestStream;

	private InetAddress ina;
	private Socket s;

	private boolean status;

	public GameViewer(int id) {

		try {
			ina = InetAddress.getByName(hostName);
		} catch (UnknownHostException u) {
			System.out.print("Cannot find host name");
			System.exit(0);
		}

		try {
			s = new Socket(ina, DEFAULT_PORT);
		} catch (IOException ex) {
			System.out.print("Cannot connect to host");
			System.exit(1);
		}

		// Get I/O streams make the ObjectStreams
		// for serializable objects
		try {
			responseStream = new ObjectInputStream(s.getInputStream());
			requestStream = new ObjectOutputStream(s.getOutputStream());
			requestStream.flush();
		} catch (IOException io) {

			System.out.println("Failed to get socket streams");
			System.exit(1);
		}

		this.status = true;

	}


	private Receiver rec;

	PlayersData playerdata = new PlayersData();
	
	public void run() {
		
		// player user login
		@SuppressWarnings("unused")
		String playerUsername, playerPassword;
		@SuppressWarnings("unused")
		boolean isAccount = false;
		System.out.println("Starting viewer client");
		rec = new Receiver();
		rec.start();
	}

	private class Receiver extends Thread {

		public void run() {
                 new GameModule().run(); // Not sure what to do

			while (status) {
				try {
                                    
					Message response = (Message) responseStream.readObject();
					
					
					if(response.getControl().equals("Message"))
					{
						System.out.println(response.getAssociatedData());
					}
					else if(response.getControl().equals("Quit"))
					{
						status = false;
					}
				} catch (IOException e) {

					status = false;
					System.out.println("Stream closed");
				} catch (ClassNotFoundException e) {

					e.printStackTrace();
				} 
			}
		
			end();
			System.out.println("GameViewer ends");
		}
		
		//clean up here
		public void end(){
			
			try {
				requestStream.close();
				responseStream.close();
				s.close();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		
	}
	

	public static void main(String[] args) {
		
		new GameViewer(1).run();
	}


}
