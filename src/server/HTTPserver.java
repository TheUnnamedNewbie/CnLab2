package server;

import java.net.*;
import server.thread.socketThread;

/*
 * Main server class.
 * 
 * Will try and connect to server (currently undefined what server) on port 80 and GET /index.html or such.
 * 
 */


class HTTPserver {
	
	private static final String target = "www.esat.kuleuven.be";
	
	
	//Defenitions:
	private static final int socketPort = 80;
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
