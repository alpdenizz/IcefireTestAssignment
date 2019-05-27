package icefire.TestAssignment.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Encrypted {

	@Id
	@GeneratedValue
	private long id;
	
	@NotBlank
	private String encrypted;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="username", nullable=false)
	private User user;
	
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
	
	public void setUser(User user) {
		this.user = user;
	}
}
