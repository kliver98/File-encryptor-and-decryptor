package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt {
	
	public static final int SALT = 24;
	public static final int ITERATIONS = 1000;
	public static final int LENGTH = 128;
	
	private Program program;
	
	public Encrypt() {
		program = new Program();
	}
	
	//Encrypt the password
	public String passEncrypt(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		char[] pass = password.toCharArray();
		byte[] salt = salt();
		
		byte[] pbkPass = pbkdf2(pass, salt, ITERATIONS, LENGTH);
		
		return ITERATIONS + ":" + toHex(salt) + ":" + toHex(pbkPass);
	}
	
	//Encrypt the password
		public String passEncryptWithSalt(String password, String saltt) throws NoSuchAlgorithmException, InvalidKeySpecException {
			char[] pass = password.toCharArray();
			byte[] salt = fromHex(saltt);
			
			byte[] pbkPass = pbkdf2(pass, salt, ITERATIONS, LENGTH);
			
			return toHex(pbkPass);
		}
	
	//pbkdf2 algorithm
	public byte[] pbkdf2(char[] password, byte[] salt, int c, int dkLen) throws NoSuchAlgorithmException, InvalidKeySpecException {		
		PBEKeySpec spec = new PBEKeySpec(password, salt, c, dkLen);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] pbkdf2 = skf.generateSecret(spec).getEncoded();
		
		return pbkdf2;
	}
	
	//Generate salt
	public byte[] salt() throws NoSuchAlgorithmException {
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[SALT];
		random.nextBytes(salt);
		return salt;
	}
	
	//Convert byte to hex and return String
	public String toHex(byte[] x) {
		 BigInteger bi = new BigInteger(1, x);
	        String hex = bi.toString(16);
	        int paddingLength = (x.length * 2) - hex.length();
	        if(paddingLength > 0) {
	            return String.format("%0"  +paddingLength + "d", 0) + hex;
	        }else {
	            return hex;
	        }
	}
	
	//Convert hex String to byte hash
	private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for(int i = 0; i < binary.length; i++) {
            binary[i] = (byte)Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
        }
        return binary;
    }
	
	public void encrypt(String path, String password) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
		
		String nkey = passEncrypt(password);
		String[] parts = nkey.split(":");
		
        System.out.println(parts[1] + " salt generado encrypt");
        
        Key key = new SecretKeySpec(parts[2].getBytes(),"AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE,key);
        File file = new File(path);
		
		FileInputStream fileInputStream = new FileInputStream(file);
        byte[] inputBytes = new byte[(int)file.length()];
        fileInputStream.read(inputBytes);
        
        byte[] outputBytes = cipher.doFinal(inputBytes);
        
        File newFile = new File(path + ".encrypted");
        File sha = new File(path + ".sha");
        BufferedWriter writer = new BufferedWriter(new FileWriter(sha));
        writer.write(sha(path) + "\n" + parts[1]);
        writer.close();
        
        FileOutputStream fileOutputStream = new FileOutputStream(newFile);
        fileOutputStream.write(outputBytes);
        
        
        fileInputStream.close();
        fileOutputStream.close();
	}
	
	public void decrypt(String pathFile, String password, String ogSha) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
		
		File myObj = new File(ogSha);
		String data = "";
	    Scanner myReader = new Scanner(myObj);
	    while (myReader.hasNextLine()) {
	      data = myReader.nextLine();
	      System.out.println(data);
	    }
	    myReader.close();

        String keys = passEncryptWithSalt(password, data);
        
        Key key = new SecretKeySpec(keys.getBytes(),"AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE,key);
        File file = new File(pathFile);
		
		FileInputStream fileInputStream = new FileInputStream(file);
        byte[] inputBytes = new byte[(int)file.length()];
        fileInputStream.read(inputBytes);
        
        byte[] outputBytes = cipher.doFinal(inputBytes);
        
        File newFile = new File(pathFile + ".decrypted");
        
        File sha = new File(pathFile + ".decrypted" + ".shanew");
        BufferedWriter writer = new BufferedWriter(new FileWriter(sha));
        writer.write(sha(pathFile + ".decrypted"));
        writer.close();
        
        FileOutputStream fileOutputStream = new FileOutputStream(newFile);
        fileOutputStream.write(outputBytes);
        
        fileInputStream.close();
        fileOutputStream.close();
		
	}
	
	//SHA-1 of a file
	public String sha(String path) throws IOException, NoSuchAlgorithmException {
        FileInputStream fis = new FileInputStream(path);
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        DigestInputStream dis = new DigestInputStream(fis, md);
        byte[] bytes = new byte[1024];

        while (dis.read(bytes) > 0);

        byte[] resultByteArry = md.digest();
        return toHex(resultByteArry);
    }
	
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
		String p = "hola";
		
		Encrypt pro = new Encrypt();
		
		//String pass = pro.generateKey(p);		
		
		//System.out.println(pass);
		//boolean matched = validatePassword("hola", pass);
		
		pro.encrypt("C:\\Users\\Txus5\\Documents\\_universidad\\Semestre 8\\Seguridad\\proyecto_final\\qweqwe.txt", p);
		pro.decrypt("C:\\Users\\Txus5\\Documents\\_universidad\\Semestre 8\\Seguridad\\proyecto_final\\qweqwe.txt.encrypted", p, "C:\\Users\\Txus5\\Documents\\_universidad\\Semestre 8\\Seguridad\\proyecto_final\\qweqwe.txt.sha");
		
		
		//System.out.println(matched);
	}

}
