package server.thread;
import java.io.*;
import java.net.*;
import java.lang.Thread;



public class socketThread extends Thread {

	private Socket threadSocket;
	
	public socketThread() {}
	private boolean running = true;
	
	//Constructer for a new socketThread
	
	public socketThread(Socket handedSocket){
		this.threadSocket = handedSocket;
	}
	
	
	@Override
	public void run() {
		while(running){
		
			try{
				BufferedReader recievedStream = new BufferedReader(new InputStreamReader(threadSocket.getInputStream()));
				String recievedString = recievedStream.readLine();
				if(recievedString.contains("Stop")){
					System.out.println("Recieved stop command. Closing socket and shutting down.");
					running = false;
				}
				else {
					System.out.println("I'm a thread and I recieved :" + recievedString);
				}
			} catch(IOException e){
				System.out.println("Error! *sobs*");
			} 
		}
		try{
			this.threadSocket.close();
		} catch(Exception e) {
			System.out.println("I give up ;_;");
		}
		System.out.println("Closing");
	}
	
	

}
