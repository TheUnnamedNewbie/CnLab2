package server;

import java.io.*;
import java.net.*;

/*
 * Main server class.
 * 
 * Testing.
 * 
 * 
 */


class HTTPserver {
	
	//Defenitions:
	private static final int socketPort = 44444;
	//private boolean run = true;
	
	
	
	public static void main(String arg[]) throws Exception {
		
		//Create the socket to listen on
		ServerSocket socketServer = new ServerSocket(socketPort);
	    //BufferedReader inputConsole = new BufferedReader( new InputStreamReader(System.in));
		while(true){
			Socket connectedSocket = socketServer.accept();
			
			//Create bufferend reaser to read from the server
			BufferedReader recievedStream = new BufferedReader(new InputStreamReader(connectedSocket.getInputStream()));
		
			String recievedString = recievedStream.readLine();
			System.out.println("I Recieved: " + recievedString);
		}
	}


	
}
