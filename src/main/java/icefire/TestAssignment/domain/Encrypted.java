package icefire.TestAssignment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * 
 * @author denizalp@ut.ee
 * <p>Entity class for encrypted text values</p>
 */
@Entity
public class Encrypted {

	@Id
	@GeneratedValue
	private long id;
	
	/**
	 * Encryption result as hex encoded
	 */
	@NotBlank
	private String encrypted;
	
	public Encrypted(String encrypted) {
		this.encrypted = encrypted;
	}
	
	public Encrypted() {}
	
	public String getEncrypted() {
		return encrypted;
	}
	
	public void setEncrypted(String encrypted) {
		this.encrypted = encrypted;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
}
