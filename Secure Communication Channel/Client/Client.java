//package com.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.sun.javafx.geom.FlatteningPathIterator;

public class Client {
	private static 	String algorithm = "RC4";
	private static InetAddress serverIPAddress;
	private static int serverPort = 9998;
	private static final int BUFFER_SIZE = 1024;
	private static int secretBuffSize = 2;
	private  int password,p,g ;

public Client(int password,int p, int g ) {
		super();
		this.password = password;
		this.p = p;
		this.g = g;
	
		
	}

public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}
	


/**
 * @param privateKey_y
 * @param hostPublicKey
 * @return
 * @throws UnsupportedEncodingException 
 * @throws NoSuchAlgorithmException 
 */
private String generateSecretKey(int privateKey_y, int hostPublicKey) throws NoSuchAlgorithmException, UnsupportedEncodingException {
	String keyBeforHasing=String.valueOf(((int)Math.pow(hostPublicKey, privateKey_y))% p);
	
	return SHA1(keyBeforHasing);
}

/**
 * @param privateKey_y
 * @return
 * @throws Exception 
 */
private byte[]  generatePublicKey(int privateKey_y) throws Exception {
	String pkBeforeEncryptionString = String.valueOf(((int)Math.pow(g, privateKey_y))% p);
		String pas =String.valueOf(password);
		return	RC4Encrypt(pkBeforeEncryptionString,pas);
}


private static byte[] receiveSecretData(DatagramSocket socket) {
	
	byte[] tempReceivedData = new byte[secretBuffSize];
	DatagramPacket receiveDatagramPacket = new DatagramPacket(tempReceivedData, tempReceivedData.length);
		try {
			socket.receive(receiveDatagramPacket); // this line of code can through IOEXception.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	
	return receiveDatagramPacket.getData();
} //end receiveSecretData()


// this method sends data over the network by using UDP protocol
private static void sendSecretData(DatagramSocket socket, byte[] msg) {
	try {
		serverIPAddress = InetAddress.getByName("127.0.0.1");
		DatagramPacket senDatagramPacket = new DatagramPacket(msg, msg.length, serverIPAddress, serverPort);
		socket.send(senDatagramPacket);
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
}//end sendSecretData()


//this method sends data over the network by using UDP protocol
private static void sendData(DatagramSocket socket, String msg) {
	try {
		serverIPAddress = InetAddress.getByName("127.0.0.1");
		byte[] tempDatatoSend = new byte[msg.length()];
		tempDatatoSend = msg.getBytes();
		DatagramPacket senDatagramPacket = new DatagramPacket(tempDatatoSend, tempDatatoSend.length, serverIPAddress, serverPort);
		socket.send(senDatagramPacket);
		
		
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
}//end sendData()


private static String receiveData(DatagramSocket socket) {
	
	byte[] tempReceivedData = new byte[BUFFER_SIZE];
	DatagramPacket receiveDatagramPacket = new DatagramPacket(tempReceivedData, tempReceivedData.length);
		try {
			socket.receive(receiveDatagramPacket); // this line of code can through IOEXception.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	String receivedString = new String(receiveDatagramPacket.getData());
	return receivedString;
} //end receiveData()

private static String readFile(String fileName)  {
	
	
	String fileContentString = "" , tempString;
	
	try {
		FileReader fReader = new FileReader(fileName);
		BufferedReader bReader = new BufferedReader(fReader);
		while((tempString = bReader.readLine())!=null) {
			// condition checks the minimum length of the read line
			// Length > 6 means this line contains the password and others
			if (tempString.length()>6) {
				fileContentString = tempString;
			}
		}
		bReader.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return fileContentString;
	
	
} //endl readFile()


// taken form old one and used after some modifications
public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException
{ 
	    MessageDigest md;
	    md = MessageDigest.getInstance("SHA-1");
	    byte[] sha1hash = new byte[40];
	    md.update(text.getBytes("UTF-8"), 0, text.length());
	    sha1hash = md.digest();
	    StringBuffer buf = new StringBuffer();
	    
	     for (int i = 0; i < sha1hash.length; i++)
	     { 
	         int halfbyte = (sha1hash[i] >>> 4) & 0x0F;
	         int two_halfs = 0;
	         do
	         { 
	             if ((0 <= halfbyte) && (halfbyte <= 9)) 
	                 buf.append((char) ('0' + halfbyte));
	             else 
	                 buf.append((char) ('a' + (halfbyte - 10)));
	             halfbyte = sha1hash[i] & 0x0F;
	         } while(two_halfs++ < 1);
	     } 
	     return buf.toString();
} //end SHA1()


// taken from http://esus.com/encryptingdecrypting-using-rc4/
public static byte[] RC4Encrypt(String toEncrypt, String key) throws Exception {
	// create a binary key from the argument key (seed)
    SecureRandom sr = new SecureRandom(key.getBytes());
    KeyGenerator kg = KeyGenerator.getInstance(algorithm);
    kg.init(sr);
    SecretKey sk = kg.generateKey();

    // create an instance of cipher
    Cipher cipher = Cipher.getInstance(algorithm);

    // initialize the cipher with the key
    cipher.init(Cipher.ENCRYPT_MODE, sk);

    // enctypt!
    byte[] encrypted = cipher.doFinal(toEncrypt.getBytes());

    return encrypted;
 }

 public static String RC4Decrypt(byte[] toDecrypt, String key) throws Exception {
	// create a binary key from the argument key (seed)
     SecureRandom sr = new SecureRandom(key.getBytes());
     KeyGenerator kg = KeyGenerator.getInstance(algorithm);
     kg.init(sr);
     SecretKey sk = kg.generateKey();
 
     // do the decryption with that key
     Cipher cipher = Cipher.getInstance(algorithm);
     cipher.init(Cipher.DECRYPT_MODE, sk);
     byte[] decrypted = cipher.doFinal(toDecrypt);
 
     return new String(decrypted);
 }




public static void main(String[] args) throws Exception  {
	DatagramSocket bobClientSocket = new DatagramSocket();
	
	String fileNAmeString = "sharedCredentials.txt"; // file to open
	String fileContentString =readFile(fileNAmeString);
	String tokens[] = fileContentString.split(",");
	int password = Integer.valueOf(tokens[0]);
	int p = Integer.valueOf(tokens[1]);
	int g = Integer.valueOf(tokens[2]);
	
	Client bobClient = new Client(password,p,g);
	
	 bobClient.startSecuredChannel(bobClientSocket);
	 bobClientSocket.close();
}

public void startSecuredChannel(DatagramSocket socket) throws Exception {
	boolean flag = false;
	String wakingString = "LetsGo";
	sendData(socket, wakingString);
	
	String responceMessage = receiveData(socket).trim();
	if (responceMessage.equalsIgnoreCase("Welcome")) {
		
		
		Random random =new Random();
		
		int privateKey_y = random.nextInt(p-1) +1; // 0<key>p
		byte[] publicKey = generatePublicKey(privateKey_y); // calculating public key 
		// this size variable is use to receive the exact size of the hostpublic key byte array
		 secretBuffSize = Integer.valueOf(receiveData(socket).trim());
		byte[] hostPublicKey = receiveSecretData(socket); // receives the Host's public key
		String decryptedHostPublicKeyString = RC4Decrypt(hostPublicKey, "126435");
		int decryptHostPK = Integer.valueOf(decryptedHostPublicKeyString);
		sendData(socket, String.valueOf(publicKey.length));
		sendSecretData(socket, publicKey); // sending public key to host
		// now calcualating Secret key of client
		String clientSecretKey = generateSecretKey(privateKey_y, decryptHostPK);		
		String receivedHostSecretKey = receiveData(socket).toString().trim();
		sendSecretData(socket, clientSecretKey.getBytes()); // sending client secret key to host
		
		if (clientSecretKey.equals(receivedHostSecretKey)) {
			
			System.out.println("Secured Connection Established");
			flag= true;
		}
		
		if (flag) {
			
			// gets  user input.
			BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
			while (flag) {
				
				String textToSend = bReader.readLine();
				 if (textToSend.equalsIgnoreCase("exit")) {
					 String k_m = clientSecretKey + textToSend;
						String h = SHA1(k_m);
						String m_h = textToSend + "!k!" + h;
						byte[] cipherText_C = RC4Encrypt(m_h, clientSecretKey);
						sendData(socket, String.valueOf(cipherText_C.length));
						sendSecretData(socket, cipherText_C);
					 
					 break; // this will close the connection and print bye on server's Screen.
				}
				 // client dispatch message after encrypting the text.
				String k_m = clientSecretKey + textToSend;
				String h = SHA1(k_m);
				String m_h = textToSend + "!k!" + h;
				byte[] cipherText_C = RC4Encrypt(m_h, clientSecretKey);
				sendData(socket, String.valueOf(cipherText_C.length));
				sendSecretData(socket, cipherText_C);
				
				// client waits for message.
				secretBuffSize = Integer.parseInt(receiveData(socket).trim());
				byte[] receivedCipherTxt = receiveSecretData(socket);
				String _m_h = RC4Decrypt(receivedCipherTxt, receivedHostSecretKey);
				String tokens[] = _m_h.split("!k!");
				String messageString = tokens[0];
				String hString = tokens[1];
				String kPrime = receivedHostSecretKey + messageString;
				String hPrime = SHA1(kPrime);
				if (hPrime.equals(hString)) {

					System.out.println(messageString);
				} else {
					System.out.println("Decryption Error");
				} 
			}
		}
	} // end connection established if condition
	
	
	
}// endl startSecuredChannel()

}
