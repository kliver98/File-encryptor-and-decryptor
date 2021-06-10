package control;

import model.Program;
import view.Status;

public class Controller {

	/**
	 * Relation with main class in model
	 */
	private Program model;
	
	/**
	 * Constructor that initialize model
	 */
	public Controller() {
		model = new Program();
	}
	
	/**
	 * Method to be called when a file wants to be cipher
	 * @param String path of file to be cipher
	 * @return String status if everything was good or has any problems
	 */
	public String cipherFile(String path, String password) {
		return Status.WAITING.toString();
	}
	
	/**
	 * Method to be called when a file wants to be decipher
	 * @param String path of file to be decipher
	 * @param String password to decipher
	 * @return String status if everything was good or has any problems
	 */
	public String decipherFile(String path, String password, String pathToSHA1) {
		return Status.WAITING.toString();
	}
	
	/**
	 * Method that checks if text its on SHA1 format 
	 * @param String hash to check
	 * @return Boolean true if its a valid hash or false otherwise
	 */
	public static boolean isValidSHA1(String hash) {
	    return Program.isValidSHA1(hash);
	}
	
}
