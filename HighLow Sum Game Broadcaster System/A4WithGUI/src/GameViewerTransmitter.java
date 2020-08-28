

import java.io.*;
import java.net.*;

public class GameViewerTransmitter implements Runnable{

	private Socket sock;
	//private ObjectInputStream requests;
	private ObjectOutputStream responses;
        

	public GameViewerTransmitter(Socket sock) {
		this.sock = sock;
                

		try {

			responses = new ObjectOutputStream(sock.getOutputStream());
			responses.flush();
			//requests = new ObjectInputStream(sock.getInputStream());

		} catch (IOException io) {
			System.out.println("Cannot open stream");

			try {
				sock.close();
			} catch (Exception e) {
			}
			System.exit(0);
		}

	}

	public void sentMessage(Message message) {

		try {
			responses.writeObject(message);
			responses.flush();
            responses.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	
	public void end() {

		try {
			responses.close();
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    @Override
    public void run() {
    }

    
}
