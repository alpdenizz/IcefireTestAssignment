package icefire.TestAssignment.resource;

import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import icefire.TestAssignment.domain.Encrypted;
import icefire.TestAssignment.service.EncryptedService;
import icefire.TestAssignment.util.EncryptedUtil;

/**
 * 
 * @author denizalp@ut.ee
 * <p>API for the operations in EncryptedService</p>
 */
@RestController
@CrossOrigin
public class EncryptedResource {
	
	@Autowired
	private EncryptedService service;
	
	private SecretKey key;
	
	/**
	 * Constructor in order to set SecretKey
	 * @throws Exception
	 */
	public EncryptedResource() throws Exception {
		key = EncryptedUtil.getSecretKey();
	}
	
	/**
	 * 
	 * @param e Encrypted object including only text to be encrypted
	 * @return Encrypted object with the encrypted text, if input's text is not empty<br>
	 * Otherwise, it will return 400
	 * @throws Exception
	 */
	@PostMapping("/encrypt")
	public ResponseEntity<?> saveEncryption(@Valid @RequestBody Encrypted e) throws Exception{
		if(e.getEncrypted().trim().isEmpty()) return new ResponseEntity<String>("Parameter text must not be blank", HttpStatus.BAD_REQUEST);
		String text = e.getEncrypted();
		Cipher c = EncryptedUtil.getCipherForEncryption(key);
		return new ResponseEntity<Encrypted>(service.encrypt(text, c),HttpStatus.OK);
		
	}
	
	/**
	 * 
	 * @param text going to be decrypted (must not be blank!)
	 * @return Encrypted object with the decrypted text with id=0<br>
	 * If input text is blank, then returns 400
	 * @throws Exception
	 */
	@GetMapping("/decrypt")
	public ResponseEntity<?> decrypt(@RequestParam("text") String text) throws Exception{
		
		if(text.trim().isEmpty()) return new ResponseEntity<String>("Parameter text must not be blank", HttpStatus.BAD_REQUEST);
		Cipher c = EncryptedUtil.getCipherForDecryption(key);
		Encrypted e = new Encrypted();
		e.setEncrypted(service.decrypt(text, c));
		e.setId(0);
		return new ResponseEntity<Encrypted>(e,HttpStatus.OK);
	}
	
	/**
	 * 
	 * @return list of Encrypted values in DB
	 */
	@GetMapping("/")
	public ResponseEntity<?> getAllValues() {
		return new ResponseEntity<List<Encrypted>>(service.getAllValues(),HttpStatus.OK);
	}

}
