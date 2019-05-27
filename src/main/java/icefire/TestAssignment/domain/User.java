package icefire.TestAssignment.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class User {

	@Id
	@NotBlank
	private String username;
	
	@OneToMany(mappedBy="user",cascade = CascadeType.ALL)
	private List<Encrypted> values = new ArrayList<Encrypted>();
	
	public User(String un) {
		this.username = un;
	}
	public String getUsername() {
		return username;
	}
	public List<Encrypted> getValues() {
		return values;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setValues(List<Encrypted> values) {
		this.values = values;
	}
	public User() {
		// TODO Auto-generated constructor stub
	}
}
