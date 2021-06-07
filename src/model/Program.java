package model;

public class Program {

	public Program() {
		
	}
	
	public static boolean isValidSHA1(String hash) {
	    return hash.matches("^[a-fA-F0-9]{40}$");
	}
	
}
