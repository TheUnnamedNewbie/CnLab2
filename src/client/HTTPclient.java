package client;
import java.io.*;
import java.net.*;


/*
 * HTTP main class for the client. 
 * Test: opens up a connection to localhoast port 44444
 * localhoast: 127.0.0.1
 */

class HTTPclient {
	
	//Defenitions:
	private static final int socketPort = 44444;
	
	
	
	
	public static void main(String arg[]) throws Exception
	{
	
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	System.out.print("From Console: ");
	
	String inputUser = input.readLine();
	System.out.println("I sent: " + inputUser);
	
	Socket connectionSocket = new Socket("localhost", socketPort);
	DataOutputStream outputServer = new DataOutputStream(connectionSocket.getOutputStream());
	outputServer.writeBytes(inputUser + '\n');
	connectionSocket.close();
	
	
	
	}
	

}
