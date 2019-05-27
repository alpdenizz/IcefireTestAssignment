package icefire.TestAssignment.resource;

import java.security.KeyPair;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
import icefire.TestAssignment.domain.User;
import icefire.TestAssignment.service.EncryptedService;
import icefire.TestAssignment.service.UserService;
import icefire.TestAssignment.util.EncryptedUtil;

@RestController
@CrossOrigin
public class EncryptedResource {
	
	@Autowired
	private EncryptedService service;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/encrypt")
	public ResponseEntity<?> saveEncryption(@Valid @RequestBody Encrypted e) throws Exception{
		User current = userService.getCurrentUser();
		if(current == null) {
			return new ResponseEntity<String>("Not authenticated",HttpStatus.UNAUTHORIZED);
		}
		else {
			SecretKey sk = EncryptedUtil.obtainSecretKeyFrom(current.getUsername());
			String text = e.getEncrypted();
			//System.out.println("Encrypted text: "+text);
			Cipher c = EncryptedUtil.getCipherForEncryption(sk);
			return new ResponseEntity<Encrypted>(service.encrypt(text, c,current),HttpStatus.OK);
		}
	}
	
	@GetMapping("/decrypt")
	public ResponseEntity<?> decrypt(@RequestParam("text") String text) throws Exception{
		User current = userService.getCurrentUser();
		if(current == null) {
			return new ResponseEntity<String>("Not authenticated",HttpStatus.UNAUTHORIZED);
		}
		else {
			if(text.trim().isEmpty()) return new ResponseEntity<String>("Parameter text must not be blank", HttpStatus.BAD_REQUEST);
			SecretKey key = EncryptedUtil.obtainSecretKeyFrom(current.getUsername());
			Cipher c = EncryptedUtil.getCipherForDecryption(key);
			Encrypted e = new Encrypted();
			e.setEncrypted(service.decrypt(text, c));
			e.setId(0);
			return new ResponseEntity<Encrypted>(e,HttpStatus.OK);
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllValues() {
		User current = userService.getCurrentUser();
		if(current == null) {
			return new ResponseEntity<String>("Not authenticated",HttpStatus.UNAUTHORIZED);
		}
		else {
			return new ResponseEntity<List<Encrypted>>(service.valuesOfUser(current.getUsername()),HttpStatus.OK);
		}
	}

}
