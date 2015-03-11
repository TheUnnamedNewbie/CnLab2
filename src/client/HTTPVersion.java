package client;

public enum HTTPVersion {
	ONE, TWO;
	
	@Override
	public String toString(){
		switch(this){
		case ONE: return "HTTP/1.0";
		case TWO: return "HTTP/1.1";
		default: return "";
		}		
	}
	
	
}
