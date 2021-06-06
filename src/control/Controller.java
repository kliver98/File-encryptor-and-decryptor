package control;

import java.io.File;

import model.Program;
import view.Status;

public class Controller {

	private Program model;
	
	public Controller() {
		model = new Program();
	}
	
	public String cipherFile(File file) {
		return Status.SUCCESS.toString();
	}
	
	public String decipherFile(File file) {
		return Status.SUCCESS.toString();
	}
	
	public boolean isValidSHA1(String s) {
	    return model.isValidSHA1(s);
	}
	
}
