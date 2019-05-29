package icefire.TestAssignment.service;


import java.util.List;
import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import icefire.TestAssignment.domain.Encrypted;
import icefire.TestAssignment.repository.EncryptedRepository;


/**
 * 
 * @author denizalp@ut.ee
 * <p>Encryption, decryption and getting all encrypted values</p>
 */
@Service
public class EncryptedService {
	
	@Autowired
	private EncryptedRepository repository;
	
	/**
	 * 
	 * @param text going to be encrypted
	 * @param cipher Java object for the operation
	 * @return Encrypted entity that is saved in the end
	 * @throws Exception
	 */
	public Encrypted encrypt(String text, Cipher cipher) throws Exception{
		byte[] ciphered = cipher.doFinal(text.getBytes());
		String encryptedString = Hex.encodeHexString(ciphered);
		Encrypted encrypted = new Encrypted(encryptedString);
		return repository.save(encrypted);
	}
	
	/**
	 * 
	 * @param encrypted going to be decrypted
	 * @param cipher Java object for the operation
	 * @return Decrypted value as String
	 * @throws Exception
	 */
	public String decrypt(String encrypted, Cipher cipher) throws Exception{
		byte[] ciphered = Hex.decodeHex(encrypted);
		String decrypted = new String(cipher.doFinal(ciphered));
		return decrypted.trim();
	}
	
	/**
	 * 
	 * @return list of Encrypted saved in DB
	 */
	public List<Encrypted> getAllValues() {
		return repository.findAll();
	}

}
