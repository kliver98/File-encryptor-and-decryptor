package control;

import model.Program;
import view.Status;

public class Controller {

	private Program model;
	
	public Controller() {
		model = new Program();
	}
	
	public String cipherFile(String path) {
		return Status.SUCCESS.toString();
	}
	
	public String decipherFile(String path, String password) {
		return Status.SUCCESS.toString();
	}
	
	public boolean isValidSHA1(String s) {
	    return model.isValidSHA1(s);
	}
	
}
