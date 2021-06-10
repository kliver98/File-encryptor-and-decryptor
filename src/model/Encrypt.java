package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt {
	
	public static final int SALT = 24;
	public static final int ITERATIONS = 1000;
	public static final int LENGTH = 128;
	
	public Encrypt() {
		
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
	
	public String encrypt(String path, String password) {
		String status = "Cifrado exitoso \nEn el mismo folder del archivo se encuentran los archivos cifrados";
		BufferedWriter writer = null;
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		
		try {
			File file = new File(path);
			if(!file.exists()) {
				status = "No se pudo leer el archivo " + path;
				throw new Exception(status);
			} else if(password == null || password.isEmpty()) {
				status = "Contrasenia no valida";
				throw new Exception(status);
			}
			String nkey = passEncrypt(password);
			String[] parts = nkey.split(":");
			
			//System.out.println(parts[1] + " salt generado encrypt");
			
			Key key = new SecretKeySpec(parts[2].getBytes(),"AES");
			
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE,key);
			
			fileInputStream = new FileInputStream(file);
			byte[] inputBytes = new byte[(int)file.length()];
			fileInputStream.read(inputBytes);
			
			byte[] outputBytes = cipher.doFinal(inputBytes);
			
			File newFile = new File(path + ".encrypted");
			File sha = new File(path + ".sha");
			writer = new BufferedWriter(new FileWriter(sha));
			writer.write(sha(path) + "\n" + parts[1]);
			
			fileOutputStream = new FileOutputStream(newFile);
			fileOutputStream.write(outputBytes);
			
			writer.close();
			fileInputStream.close();
			fileOutputStream.close();	
		} catch (Exception e) {
			status = "Ocurrio un error\n" + e.getMessage();
		}
		return status;
	}
	
	public String decrypt(String pathFile, String password, String ogSha) {
		String status = "Descifrado exitoso \nEn el mismo folder del archivo se encuentra el archivo cifrado";
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		String decryptedSha = "";
		String[] data = new String[2];
		
		try {
			File file = new File(pathFile);
			if(!file.exists()) {
				status = "No se pudo leer el archivo " + pathFile;
				throw new Exception(status);
			} else if(password == null || password.isEmpty()) {
				status = "Contrasenia no valida";
				throw new Exception(status);
			}
			File myObj = new File(ogSha);
			if(!myObj.exists()) {
				status = "No se pudo leer el archivo " + ogSha;
				throw new Exception(status);
			}
			Scanner myReader = new Scanner(myObj);
			//sha-1
			data[0] = myReader.nextLine();
			//salt
			data[1] = myReader.nextLine();
			
			myReader.close();
			
			String keys = passEncryptWithSalt(password, data[1]);
			
			Key key = new SecretKeySpec(keys.getBytes(),"AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE,key);
			
			fileInputStream = new FileInputStream(file);
			byte[] inputBytes = new byte[(int)file.length()];
			fileInputStream.read(inputBytes);
			
			byte[] outputBytes = cipher.doFinal(inputBytes);
			
			File newFile = new File(pathFile + ".decrypted");
			
			fileOutputStream = new FileOutputStream(newFile);
			fileOutputStream.write(outputBytes);
			
			fileInputStream.close();
			fileOutputStream.close();
			
			decryptedSha = sha(pathFile + ".decrypted");
			
		} catch (Exception e) {
			status = "Ocurrio un error\n" + e.getMessage();
			return status;
		}
		if(decryptedSha.equals(data[0])) {
			status += "\nLos codigos SHA-1 son iguales\n" + data[0];
		}
		return status;
	}
	
	//SHA-1 of a file
	public String sha(String path) throws IOException, NoSuchAlgorithmException {
        FileInputStream fis = new FileInputStream(path);
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        DigestInputStream dis = new DigestInputStream(fis, md);
        byte[] bytes = new byte[1024];

        while (dis.read(bytes) > 0);

        byte[] resultByteArry = md.digest();
        dis.close();
        return toHex(resultByteArry);
    }

}
