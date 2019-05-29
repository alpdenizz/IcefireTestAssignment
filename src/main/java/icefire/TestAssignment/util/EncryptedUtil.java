package icefire.TestAssignment.util;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * 
 * @author denizalp@ut.ee
 * <p>Utility class with static methods used in the operations</p>
 *
 */
public class EncryptedUtil {
	
	/**
	 * 
	 * @return a SecretKey for operations
	 * @throws Exception
	 */
	public static SecretKey getSecretKey() throws Exception{
		KeyGenerator gen = KeyGenerator.getInstance("AES");
		gen.init(128);
		return gen.generateKey();
	}
	
	/**
	 * 
	 * @param key used in Cipher object
	 * @return Cipher object that will be used for encryption
	 * @throws Exception
	 */
	public static Cipher getCipherForEncryption(SecretKey key) throws Exception{
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher;
	}
	
	/**
	 * 
	 * @param key key used in Cipher object
	 * @return Cipher object that will be used for decryption
	 * @throws Exception
	 */
	public static Cipher getCipherForDecryption(SecretKey key) throws Exception{
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher;
	}

}
