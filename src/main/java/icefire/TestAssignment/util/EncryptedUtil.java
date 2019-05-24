package icefire.TestAssignment.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class EncryptedUtil {
	
	public static SecretKey getSecretKey() throws Exception{
		KeyGenerator gen = KeyGenerator.getInstance("AES");
		gen.init(128);
		return gen.generateKey();
	}
	
	public static Cipher getCipherForEncryption(SecretKey key) throws Exception{
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher;
	}
	
	public static Cipher getCipherForDecryption(SecretKey key) throws Exception{
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher;
	}
	
	public static String makeMultipleOf16(String text) {
		int len = text.length();
		int amount = len % 16;
		for(int i=0; i<amount; i++) {
			text += " ";
		}
		return text;
	}

}
