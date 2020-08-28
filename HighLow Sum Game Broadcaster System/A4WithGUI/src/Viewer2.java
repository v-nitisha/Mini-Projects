
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Viewer2 {
    private static final int DEFAULT_PORT = 4444;
	private static final String hostName = "localhost";

	private ObjectInputStream responseStream;
	private ObjectOutputStream requestStream;

	private InetAddress ina;
	private Socket s;

	private boolean status;

	public Viewer2(int id) {

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
		String playerUsername, playerPassword;
		boolean isAccount = false;

		ViewerLogin vw = new ViewerLogin();
		System.out.println("Starting viewer client");
		rec = new Receiver();
		rec.start();
	}

	private class Receiver extends Thread {

		public void run() {
                    new GameModule().run();

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
		
		new Viewer2(1).run();
	}


    
}
