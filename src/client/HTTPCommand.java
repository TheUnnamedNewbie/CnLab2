package client;

public enum HTTPCommand {
	HEAD, GET, POST, PUT, INVALID;

	@Override
	public String toString(){
		switch(this){
		case HEAD: return "HEAD"; 
		case GET: return "GET";
		case POST: return "POST";
		case PUT: return "PUT";
		default: return "";
		}
	}

}

