package client;
import java.io.*;
import java.net.*;

/*
 * HTTP main class for the client. 
 * Test: opens up a connection to localhoast port 44444
 * 
 */

class HTTPclient {
	
	
	private static final int nbCommands = 3; //amount of commands the original call of server should contain.
	
	
	//CommandSettings:
	public static HTTPCommand commandType;
	public static int port;
	public static String version;
	
	
	
	public static void main(String arg[]) throws Exception
	{	
		//Parsing of data
			if(arg.length != nbCommands){ //Checking if enought commands are given.
				toConsole("Not enought data given. Please try again.");
			} else{
				commandType = parseVersion(arg[0]); //find out what type of HTTP command you have given.
				if(commandType == HTTPCommand.INVALID){ //check if it was an actual legal type
					toConsole("Invalid HTTP command. Try again."); //abort if not legal
				} else { 
					boolean legalPort = parsePort(arg[2]); //find out what port you have given me
					if(legalPort){ //if its a good port number, continue
						parseInputURI(arg[1]);
					}
				}
			} 
			
	}
	
	
	/*********************************
	 *******ARGUMENT PARSING**********
	 ********************************/
	
	
	/**
	 * Sets the version of command this client has to excecute.
	 * @param version
	 * @return HTTPCommand.version, version is invalid if not valid
	 */
	private static HTTPCommand parseVersion(String version){
		if(version.contains("GET")) {return HTTPCommand.GET;}
		else if(version.contains("HEAD")) {return HTTPCommand.HEAD;}
		else if(version.contains("PUT")) {return HTTPCommand.PUT;}
		else if(version.contains("POST")) {return HTTPCommand.POST;}
		return HTTPCommand.INVALID;
	}
	
	
	/**
	 * Sets the port given at startup.
	 * @param portString
	 * @return a boolean indicating if the given port was a legal one.
	 */
	private static boolean parsePort(String portString){
		try{
			port = Integer.parseInt(portString);
		} catch(NumberFormatException e) {toConsole("Port not a number!"); return false;}
		if(port < 1){toConsole("Bad port number! Try again"); return false;}
		if(port > 65535){toConsole("Bad port number! Try again"); return false;}
		return true;
	}
	
	
	
	private static void parseInputURI(String inputURI){
		
	}
	
	
	
	
	private static void toConsole(String data){
		System.out.println(data);
	}
	
	
	
	//TESTMETHOD NOT SUPPOSED TO BE IN FINAL PRODUCT
		public static void printCommand() {
			if(commandType == HTTPCommand.GET){System.out.println("Command is GET");}
			else if(commandType ==HTTPCommand.HEAD){System.out.println("Command is HEAD");}
			
		}
}

/*
		
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
*/