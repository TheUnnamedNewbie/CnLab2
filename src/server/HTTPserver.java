package server;

import java.net.*;
import server.thread.socketThread;

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
			if(connectedSocket != null){
				
				socketThread t = new socketThread(connectedSocket);
				t.start();
			
			}
		}
	}


	
}
