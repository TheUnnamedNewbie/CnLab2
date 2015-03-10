package client;
import java.io.*;
import java.net.*;

/*
 * HTTP main class for the client. 
 * Test: opens up a connection to localhoast port 44444
 * 
 */

class HTTPclient {
	
	
	
	//CommandSettings:
	public static HTTPCommand commandType;
	public int port;
	public String version;
	
	
	
	public static void main(String arg[]) throws Exception
	{	
		/*parsing arguments. Room for improvement. Currently just checks if there are enought and then continues, things can be edited later to be more flexible.
		 *
		 */
			if(arg.length < 2){
				commandType = parseVersion(arg[0]);
			} else {toConsole("You're an idiot");}
			printCommand();
			
	}
	
	public static void printCommand() {
		if(commandType == HTTPCommand.GET){System.out.println("Command is GET");}
		else if(commandType ==HTTPCommand.HEAD){System.out.println("Command is HEAD");}
		
	}
	
	/**
	 * Sets the version of command this client has to pull off.
	 * @param version
	 * @return
	 */
	private static HTTPCommand parseVersion(String version){
		if(version.contains("GET")) {return HTTPCommand.GET;}
		else if(version.contains("HEAD")) {return HTTPCommand.HEAD;}
		else if(version.contains("PUT")) {return HTTPCommand.PUT;}
		else if(version.contains("POST")) {return HTTPCommand.POST;}
		return HTTPCommand.INVALID;
	}
	//private int countWhitespaces(String data[]){
	
	private static void toConsole(String data){
		System.out.println(data);
	}
		
		
		
//		
//		{	
//		
//		// setting up IO
//		Socket connectionSocket = new Socket("www.esat.kuleuven.be", socketPort);
//		DataOutputStream outputServer = new DataOutputStream(connectionSocket.getOutputStream());
//		BufferedReader recievedData = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
//		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));		
//		
//		while(running){
//	
//			System.out.print("From Console: ");
//			
//			String inputUser = input.readLine();
//			outputServer.writeBytes("GET /cde/bestelaanvraag_elcomp_NL.html" + "\n");
//			outputServer.writeBytes("Host: www.esat.kuleuven.be" + "\n");
//			outputServer.writeBytes("\n");
//		
//			if(inputUser.contains("Stop")){
//				running = false;
//				System.out.println("Sending Stop Command to servern...");
//				outputServer.writeBytes("Stop");
//				System.out.println("Stop signal sent....");
//				System.out.println("closing socket...");
//				}
//			
//			while(true){
//				String recievedDataString = recievedData.readLine();
//				if(recievedDataString != null){
//					System.out.println(recievedDataString);
//				}
//			}
//			
//						
//		}
//	connectionSocket.close();
//	System.out.println("Socket closed succesfully. Goodbye.");
//	
//	} 
	

}
