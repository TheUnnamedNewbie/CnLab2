package client;
import java.io.*;
import java.net.*;

/*
 * HTTP main class for the client. 
 * 
 * 
 * TODO:
 * 		- Parse HTML data for embedded images
 * 		- Implement if-modified-since
 * 		- Make structure for parsing paths and such.
 * 
 * 
 * 
 * 
 * 
 */

class HTTPclient {
	
	
	private static final int nbCommands = 4; //amount of commands the original call of server should contain.
	private static final String clientVersion = "0.1.1";
	private static final String EOL = "\n";
	
	//CommandSettings:
	public static HTTPCommand commandType;
	public static int port;
	public static HTTPVersion HTTPversion;
	public static URI currentURI;
	
	//Socket-Related stuffs.
	private static Socket clientSocket;
	private static DataOutputStream outputClient;
	private static BufferedReader bufferedInputReaderClient;
	
	
	//File-Related stuffs
	private static File log = new File("log.txt"); //contains previously visited domains (to check if pages might exist or not).
	
	public static void main(String arg[]) throws Exception
	{	
		//init interface
		toConsole("\n");
		toConsole("VaesClient (tm) " + clientVersion);
		toConsole("This is terrible software and will likely break. Use at own risk, and don't come blaming me if it ruins your marriage. \n");
		toConsole("\n");
		
	
		
		if(!log.exists()){
			toConsole("First time running program! Making logfile...");
			try{	
				log.createNewFile();
			} catch(IOException e){toConsole("Failed to create logfile! Will not store pages for furture reference.");}
		}

		
		
		//Parsing of data
			if(arg.length != nbCommands){ //Checking if enough commands are given.
				if(arg.length == 0){
					toConsole("To use this client, use the following command style: \n \n  HTTPCommand URI Port HTTPversion \n\n");
				} else{ toConsole("I can't understand your commands. Use the following structure: \n \n  HTTPCommand URI Port HTTPversion \n \n If you did use this structure, Check if the data was correct. If so, well, Tought luck");}
			} else {
				commandType = parseCommand(arg[0]); //find out what type of HTTP command you have given.
				if(commandType == HTTPCommand.INVALID){ //check if it was an actual legal type
					toConsole("Invalid HTTP command. Try again."); //abort if not legal
				} else { 
					boolean legalPort = parsePort(arg[2]); //find out what port you have given me
					if(legalPort){ //if its a good port number, continue
						currentURI = parseInputURI(arg[1]);
						if(currentURI != null){
							boolean legalVersion = parseVersion(arg[3]);
							if(legalVersion){}
							
						} else toConsole("Invalid Version");
					} else toConsole("Invalid Port");
				}
			
			setupSocket();
			doCommand();
			}
			
			
	}
	
	
	/*********************************
	 *******ARGUMENT PARSING**********
	 ********************************/
	
	
	/**
	 * Sets the version of command this client has to execute.
	 * @param version
	 * @return HTTPCommand.version, version is invalid if not valid
	 */
	private static HTTPCommand parseCommand(String version){
		if(version.contains("GET")) {return HTTPCommand.GET;} //Should prolly be a switch statement.
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
	
	private static URI parseInputURI(String inputURI){
		
		int indexOfScheme = 0;
		int indexOfPath = 0;
		int indexOfAuthority = 0;
		
		String URIScheme;
		String URIAuthority;
		String URIPath;
/*		String URIQuerry; */
	
		if(inputURI.contains("://")){ //Check if there is a scheme
			
			indexOfScheme = inputURI.indexOf("://");
		
			URIScheme = inputURI.substring(0, indexOfScheme);
			indexOfAuthority= indexOfScheme + 3;
		
		} else {URIScheme = null;}
		
		if(inputURI.substring(indexOfAuthority).contains("/")){ //TODO: See if I have to chop of / 
			
			indexOfPath = inputURI.indexOf("/", indexOfAuthority);
			URIPath = inputURI.substring(indexOfPath);
								
		} else { URIPath= "";}
		
		if(indexOfPath == 0){
			URIAuthority = inputURI.substring(indexOfAuthority);
		} else {
			URIAuthority = inputURI.substring(indexOfAuthority, indexOfPath);
		}
		
		try{
			return new URI(URIScheme, URIAuthority, URIPath, null, null);
			
		} catch(URISyntaxException e){toConsole("Bad URI! Try again!"); return null;}
		
	}
	
	private static boolean parseVersion(String versionString){
		if(versionString.contains("1.0")){
			HTTPversion = HTTPVersion.ONE;
			return true;
		} else if(versionString.contains("1.1")){
			HTTPversion = HTTPVersion.TWO;
			return true;
		} else return false;
	}
	
	private static void setupSocket(){
		try{
			clientSocket = new Socket(currentURI.getAuthority(), port);
			outputClient = new DataOutputStream(clientSocket.getOutputStream());
			bufferedInputReaderClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		} catch(UnknownHostException e) {
			throw new IllegalArgumentException("Failed to resolve host. Please forgive me.");
		} catch(IOException e){
			toConsole("IOException - must shutdown.");
		}
	}
	
	
	
	
	
	private static void doCommand(){
		toConsole("Cheking if page locally availible" + EOL);

		//temporary, should be improved later
		
			File temp = new File(currentURI.getAuthority());
			if(!temp.exists()){
				temp.mkdir();
				temp.setWritable(true);
			} temp.setWritable(true);
			
		
		if(currentURI.getPath()== ""){
			//TODO: should try index.html then index.htm then default.htm
		}
			
			
		String pathOfFile = makePath(currentURI.getPath());
		File destination = new File(pathOfFile);
		if(!(destination.exists())){
			try{
				destination.getParentFile().mkdirs(); 
				toConsole("Making Dirs!");
				destination.createNewFile();
			} catch(IOException e){toConsole("Failed to create dir");}
		}

		toConsole("Getting page \n");
		cloneConsole(commandType.toString() + " " + currentURI.getPath() + " " + HTTPversion.toString() + " " + EOL);
		
		if((commandType == HTTPCommand.GET) || (commandType == HTTPCommand.HEAD)){
			cloneConsole("Host: " + currentURI.getAuthority() + EOL);
			cloneConsole(EOL);
		} else {
			try{
				String data = getInput();
				outputClient.writeBytes(data);
			} catch(IOException e){toConsole(e.toString());}
		}
		BufferedWriter clear;
		try {
			clear = new BufferedWriter(new FileWriter(destination));
			clear.write("");
			clear.close();
			} catch (IOException e1) {
			e1.printStackTrace();
		}
	
		StringBuilder fileString = new StringBuilder();
		boolean notDone = true;
		int recievedLength = 0;
		int contentLength = 0;
		boolean started = false;
		boolean usesContentLength = false;
		try{
			while(notDone){ //TODO: Fix this - only works with ContentLength, at the moment does not terminate on a normal file.
				String recievedDataString = bufferedInputReaderClient.readLine();
				if(started){
					fileString.append(recievedDataString + "\r" + EOL);
				} else {toConsole(recievedDataString);}
				toConsole(String.valueOf((contentLength - recievedLength)));
				if(recievedDataString.contains("Content-Length:")){
					contentLength = Integer.parseInt(recievedDataString.substring(16))+1;
					usesContentLength = true;
				}
				if(!started && recievedDataString.equals("")){
					started=true;
					recievedLength = 0;
					if(commandType == HTTPCommand.HEAD){
						notDone = false;
					}
				}
				recievedLength = recievedLength + recievedDataString.length()+1;
				if(started && (contentLength == recievedLength)){
					notDone = false;
				}
			
			}
		
			//STORE STRING
			
			BufferedWriter outToDestination = new BufferedWriter(new FileWriter(destination, true));
			outToDestination.write(fileString.toString());
			outToDestination.close();
		
		} catch(IOException e) {toConsole("IOException in reading data");}
		parseImages(destination);
	
	}
	

	private static String getInput(){
		BufferedReader inputFromConsole = new BufferedReader(new InputStreamReader(System.in));
		boolean finished = false;
		String userData=null;
		toConsole("You selected " + commandType.toString() + ". What do you wish to send? \n");
		while(!finished){ 
			try{
				userData = inputFromConsole.readLine();
				toConsole("Are you sure? y/n");
				if(inputFromConsole.readLine().contains("y")){
					finished = true;
				} else {toConsole("Try again: ");}
			} catch(IOException e){toConsole("Try again");}
		}
		if(userData != null){return userData;}
		else return "";
	}
	
	
	private static void cloneConsole(String data){
		try {
			toConsole(data);
			outputClient.writeBytes(data);
		} catch(IOException e) {toConsole("IOException! I tried to send: " + data);}
		
	}
		
	
	/*
	 * Simply prints stuff in console. Quite self-explanatory.
	 */
	private static void toConsole(String data){
		System.out.println(data);
	}
	


  
  public static boolean checkForAuthority(String authority){
	  boolean result = false;
	  String line;
	  try{
	  	BufferedReader inputFromFile = new BufferedReader(new FileReader(log));
	  	while((result == false) && (line = inputFromFile.readLine()) != null){
	  		if(authority.equals(line)){
	  			result = true;
	  			toConsole("log contains authority!");
	  		}
	  	}
	  	inputFromFile.close();
	  	} catch(FileNotFoundException e){toConsole("Something went wrong! Can't find my logfile!");
	  	} catch(IOException f){toConsole("IOException in checkForAuthority");
	  	}
	  return result;
	  
  }
  
  public static void addToLog(String authority){
	  try{
		  BufferedWriter outToFile = new BufferedWriter(new FileWriter(log, true));
		  outToFile.write(authority + EOL);
		  outToFile.close();
	  } catch(FileNotFoundException e){
		  toConsole("Can't find logfile!");
	  } catch(IOException e){
		  toConsole("IOException occured in addToLog!");
	  }
	  
  }
  
  
  public static String makePath(String source){
	  int parseIndex = 0;
	  StringBuilder buildingPath = new StringBuilder();
	  buildingPath.append("." + System.getProperty("file.separator") + currentURI.getAuthority());
	  while(parseIndex < source.length()){
		  char charAtIndex = source.charAt(parseIndex); //toConsole("Index: " + String.valueOf(parseIndex) + "Char: " + String.valueOf(charAtIndex));
		  if(charAtIndex == '/') {
			  buildingPath.append(System.getProperty("file.separator"));
		  } else {
			  buildingPath.append(source.charAt(parseIndex));
		  } 
		  parseIndex++;
	  }
	  return buildingPath.toString();
  }
  
 // public static void checkExistsFile

  
  /**
   * NOTE: I assume the source file is a propperly build up HTML file (it stops parsing when it runs into <\html>
   * @param source
   */
  public static void parseImages(File source){
	boolean endOfFile = false;
	  try{
		StringBuilder newFile = new StringBuilder();
		BufferedReader inputFromFile = new BufferedReader(new FileReader(source));
		while(!endOfFile){
			String currentLine = inputFromFile.readLine();
			if(currentLine.contains("</html>")){
				endOfFile = true;
			}
			int index = 0;
			if(currentLine.substring(index).contains("<img src=")){
				int startIndex = 10 + currentLine.indexOf("<img src=", index);
				int endIndex = currentLine.indexOf("\"", startIndex);
				String imageLocation = currentLine.substring(startIndex, endIndex);
				getImage(imageLocation, source.getAbsolutePath());
			}
		}
		inputFromFile.close();
		
		
	
	
	
	
	} catch(FileNotFoundException e1){
		toConsole("I ran into a issue while locating the images. I'm sorry");
	} catch(IOException e2){
		toConsole("IOException during file-parsing");
	}
  }
	
  public static void getImage(String imageLocation, String absolutePath){
	  if(imageLocation.substring(0, 2).equals("../")){
		  toConsole("go Down one directory");
	  } else if{
		  if(imageLocation.substring(0, 2).equals("./")){
		  //stuff goes here aswell
		  }
  }
  
	//TESTMETHOD NOT SUPPOSED TO BE IN FINAL PRODUCT
		public static void printCommand() {
			if(commandType == HTTPCommand.GET){System.out.println("Command is GET");}
			else if(commandType ==HTTPCommand.HEAD){System.out.println("Command is HEAD");}
			
		}
}

