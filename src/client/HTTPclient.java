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
	private static final int socketPort = 80;
	
	private static boolean running = true;
	
	
	public static void main(String arg[]) throws Exception
	{
		
		// setting up IO
		Socket connectionSocket = new Socket("www.esat.kuleuven.be", socketPort);
		DataOutputStream outputServer = new DataOutputStream(connectionSocket.getOutputStream());
		BufferedReader recievedData = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));		
		
		while(running){
	
			System.out.print("From Console: ");
			
			String inputUser = input.readLine();
			outputServer.writeBytes("GET /cde/bestelaanvraag_elcomp_NL.html" + "\n");
			outputServer.writeBytes("Host: www.esat.kuleuven.be" + "\n");
			outputServer.writeBytes("\n");
		
			/*if(inputUser.contains("Stop")){
				running = false;
				System.out.println("Sending Stop Command to servern...");
				outputServer.writeBytes("Stop");
				System.out.println("Stop signal sent....");
				System.out.println("closing socket...");
				}
			*/
			while(true){
				String recievedDataString = recievedData.readLine();
				if(recievedDataString != null){
					System.out.println(recievedDataString);
				}
			}
			
						
		}
	connectionSocket.close();
	System.out.println("Socket closed succesfully. Goodbye.");
	
	}
	

}
