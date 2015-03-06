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
	
	private static boolean running = true;
	
	
	public static void main(String arg[]) throws Exception
	{
		
		// setting up IO
		Socket connectionSocket = new Socket("localhost", socketPort);
		DataOutputStream outputServer = new DataOutputStream(connectionSocket.getOutputStream());
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));		
		
		while(running){
	
			System.out.print("From Console: ");
			
			String inputUser = input.readLine();
			
			if(inputUser.contains("Stop")){
			
				running = false;
				System.out.println("closing socket...");			
			
			} else { 
			
				System.out.println("I sent: " + inputUser);
				outputServer.writeBytes(inputUser + '\n');
			
			}
		}
	connectionSocket.close();
	System.out.println("Socket closed succesfully. Goodbye.");
	
	}
	

}
