package model;

public class Program {
	
	private Encrypt encrypt;

	public Program() {
		encrypt = new Encrypt();
	}
	
	public static boolean isValidSHA1(String hash) {
	    return hash.matches("^[a-fA-F0-9]{40}$");
	}
	
	public String cipherFile(String path, String password) {
		return encrypt.encrypt(path, password);
	}
	
	public String decipherFile(String path, String password, String pathToSHA1) {
		return encrypt.decrypt(path, password, pathToSHA1);
	}
	
}
