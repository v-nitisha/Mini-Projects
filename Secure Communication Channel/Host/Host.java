//package com.host;

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


public class Host {
		private static 	String algorithm = "RC4";
		private static InetAddress clientIPAddress;
		private static int clientPort;
		private static int secretBuffSize = 2;
		private static final int BUFFER_SIZE = 1024;
		private  int password,p,g ;

		public Host(int password,int p, int g ) {
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
	 * @param privateKey_x
	 * @param clientPublicKey
	 * @return int return Secret key 
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	private String generateSecretKey(int privateKey_x, int clientPublicKey) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String keyBeforHasing=String.valueOf(((int)Math.pow(clientPublicKey, privateKey_x))% p);
		
			return SHA1(keyBeforHasing);
	}

	/**
	 * @param privateKey_x
	 * @return {@code int} generated public key of the host
	 * @throws Exception 
	 */
	private byte[] generatePublicKey(int privateKey_x) throws Exception {
		String pkBeforeEncryptionString = String.valueOf(((int)Math.pow(g, privateKey_x))% p);
		
		String pas =String.valueOf(password);
		return	RC4Encrypt(pkBeforeEncryptionString,pas);
	}
	
	
	// this method sends data over the network by using UDP protocol
		private static void sendSecretData(DatagramSocket socket, byte[] msg) {
			try {
				
				DatagramPacket senDatagramPacket = new DatagramPacket(msg, msg.length, clientIPAddress, clientPort);
				socket.send(senDatagramPacket);
				
				
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			
		}//end sendData()
		
		private static byte[] receiveSecretData(DatagramSocket socket) {
			
			byte[] tempReceivedData = new byte[secretBuffSize];
			DatagramPacket receiveDatagramPacket = new DatagramPacket(tempReceivedData, tempReceivedData.length);
				try {
					socket.receive(receiveDatagramPacket); 
					clientIPAddress = receiveDatagramPacket.getAddress();
					clientPort = receiveDatagramPacket.getPort();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
			
			
			return receiveDatagramPacket.getData();
		
		}
	
	
	// this method sends data over the network by using UDP protocol
	private static void sendData(DatagramSocket socket, String msg) {
		try {
			
			byte[] tempDatatoSend = new byte[msg.length()];
			tempDatatoSend = msg.getBytes();
			DatagramPacket senDatagramPacket = new DatagramPacket(tempDatatoSend, tempDatatoSend.length, clientIPAddress, clientPort);
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
				socket.receive(receiveDatagramPacket); 
				clientIPAddress = receiveDatagramPacket.getAddress();
				clientPort = receiveDatagramPacket.getPort();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		String receivedString = new String(receiveDatagramPacket.getData());
		
		return receivedString;
	
	}
	/*
	 * @param String fileName 
	 * @param String return the string read from the file
	 * 
	 * */
		
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
	
	
public static void main(String[] args) throws Exception {
		
	int hostPort = 9998;
	// create udp socket for host
	DatagramSocket aliceHostSocket = new DatagramSocket(hostPort);

	
	String fileNAmeString = "sharedCredentials.txt"; // file to open
	String fileContentString = readFile(fileNAmeString);
	String tokens[] = fileContentString.split(","); // tokenize the read line 
	int password = Integer.valueOf(tokens[0]);
	int p = Integer.valueOf(tokens[1]);
	int g = Integer.valueOf(tokens[2]);

	Host aliceHost = new Host(password, p, g);
	aliceHost.startSecuredChannel(aliceHostSocket);
	
	aliceHostSocket.close(); // closing the host socket(shutting down the server)
}
		
		
		
	
	
public void startSecuredChannel(DatagramSocket socket) throws Exception {
		
		boolean flag = false;
		String knockedMessage = receiveData(socket).trim();
		
		if ("LetsGo".equalsIgnoreCase(knockedMessage.trim())) {
			
			sendData(socket, "Welcome");
			
			Random random =new Random();
			int privateKey_x = random.nextInt(p-1) +1; // 0<key>p
			byte[] publicKey = generatePublicKey(privateKey_x);
			sendData(socket, String.valueOf(publicKey.length));
			sendSecretData(socket, publicKey); // sending public key to client
			
			secretBuffSize =Integer.parseInt(receiveData(socket).trim());
			byte[] clientPublicKey =receiveSecretData(socket); //  received public key from client.
		
			
			String decryptedCleintPublicKeyString =RC4Decrypt(clientPublicKey, "126435");
			
			int decryptClientPK = Integer.valueOf(decryptedCleintPublicKeyString); 
			
			String hostSecretKey = generateSecretKey(privateKey_x, decryptClientPK);
			
			// send sthe host Secret key to client
			sendSecretData(socket, hostSecretKey.getBytes());
			String receivedClientSecretKey = receiveData(socket).toString().trim(); // gets the client secret key
			// Both keys same means connection is secured
			if (hostSecretKey.equals(receivedClientSecretKey)) {
				
				System.out.println("Secured Connection Established");
				flag = true;
				
			}
			
			
				if (flag) {
					
					// opens the input stream for user input.
					BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
						
					// this while loop reads and write message to the channel untile client type exit
					while (flag) {
						secretBuffSize = Integer.parseInt(receiveData(socket).trim());
						byte[] receivedCipherTxt = receiveSecretData(socket);
						String _m_h = RC4Decrypt(receivedCipherTxt, receivedClientSecretKey);
						String tokens[] = _m_h.split("!k!");
						String messageString = tokens[0];
						String hString = tokens[1];
						
						
						String kPrime = receivedClientSecretKey + messageString;
						String hPrime = SHA1(kPrime);
						
						if (hPrime.equals(hString)) {
							
							System.out.println(messageString);
						} else {
							System.out.println("Decryption Error");
						}
						
						if(messageString.equalsIgnoreCase("exit")) {
							break; // if server receive exit message then server will be disconnected from the client.
						}
						String textToSend = bReader.readLine();
						String k_m = receivedClientSecretKey +textToSend;
						String h = SHA1(k_m);
						String m_h = textToSend + "!k!" + h;
						byte[] cipherText_C =RC4Encrypt(m_h, receivedClientSecretKey);
						sendData(socket, String.valueOf(cipherText_C.length));
						sendSecretData(socket, cipherText_C);
					}
				
			}
				
			
			
		}
		
	
		
	}// endl startSecuredChannel()
	
	

	
}
