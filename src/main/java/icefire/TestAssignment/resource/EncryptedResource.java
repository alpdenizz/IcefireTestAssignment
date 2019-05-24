package icefire.TestAssignment.resource;

import java.security.KeyPair;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import icefire.TestAssignment.domain.Encrypted;
import icefire.TestAssignment.service.EncryptedService;
import icefire.TestAssignment.util.EncryptedUtil;

@RestController
public class EncryptedResource {
	
	@Autowired
	private EncryptedService service;
	
	private SecretKey key;
	
	public EncryptedResource() throws Exception{
		key = EncryptedUtil.getSecretKey();
	}
	
	@PostMapping("/encrypt")
	public Encrypted saveEncryption(@Valid @RequestBody Encrypted e) throws Exception{
		String text = e.getEncrypted();
		Cipher c = EncryptedUtil.getCipherForEncryption(key);
		return service.encrypt(text, c);
	}
	
	@GetMapping("/decrypt")
	public String decrypt(@RequestParam("text") String text) throws Exception{
		Cipher c = EncryptedUtil.getCipherForDecryption(key);
		return service.decrypt(text, c);
	}
	
	@GetMapping("/")
	public List<Encrypted> getAllValues() {
		return service.getAllValues();
	}

}
