

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ViewTransmitter {

	private static final int DEFAULT_PORT = 4445;
	private static final String hostName = "localhost";
	private ObjectOutputStream requestStream;
	private InetAddress ina;
	private Socket s;
	Scanner input = new Scanner(System.in);

	public ViewTransmitter() {

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

		try {
			requestStream = new ObjectOutputStream(s.getOutputStream());
			requestStream.flush();
		} catch (IOException io) {

			System.out.println("Failed to get socket streams");
			System.exit(1);
		}
	}

	
	public void sentMessage(String message){
		
		Message m = new Message("Message",message);
		sentMessage(m);
	}
	
	public void sentMessage(Message message) {

		try {

			requestStream.writeObject(message);
			requestStream.flush();
			requestStream.reset();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public void end(){
		
		Message m = new Message("Quit","");
		sentMessage(m);		
		
		try {
			requestStream.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
