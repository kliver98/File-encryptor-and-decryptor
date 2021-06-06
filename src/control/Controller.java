package control;

import model.Program;

public class Controller {

	private Program model;
	
	public Controller() {
		model = new Program();
	}
	
	public boolean isValidSHA1(String s) {
	    return model.isValidSHA1(s);
	}
	
}
