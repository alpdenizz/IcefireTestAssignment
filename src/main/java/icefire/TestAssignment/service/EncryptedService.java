package icefire.TestAssignment.service;

import java.security.PrivateKey;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import icefire.TestAssignment.domain.Encrypted;
import icefire.TestAssignment.repository.EncryptedRepository;
import icefire.TestAssignment.util.EncryptedUtil;

@Service
public class EncryptedService {
	
	@Autowired
	private EncryptedRepository repository;
	
	public Encrypted encrypt(String text, Cipher cipher) throws Exception{
		byte[] ciphered = cipher.doFinal(text.getBytes());
		String encryptedString = Base64.getEncoder().encodeToString(ciphered);
		Encrypted encrypted = new Encrypted(encryptedString);
		return repository.save(encrypted);
	}
	
	public String decrypt(String encrypted, Cipher cipher) throws Exception{
		byte[] ciphered = Base64.getDecoder().decode(encrypted);
		String decrypted = new String(cipher.doFinal(ciphered));
		return decrypted.trim();
	}
	
	public List<Encrypted> getAllValues() {
		return repository.findAll();
	}

}
