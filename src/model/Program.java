package model;

public class Program {

	public Program() {
		
	}
	
	public boolean isValidSHA1(String s) {
	    return s.matches("^[a-fA-F0-9]{40}$");
	}
	
}
