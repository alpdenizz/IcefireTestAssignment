package icefire.TestAssignment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import icefire.TestAssignment.domain.User;
import icefire.TestAssignment.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public boolean doesUserExist(String uname) {
		if(!repository.findById(uname).isPresent()) {
			repository.save(new User(uname));
			return false;
		}
		return true;
	}
	
	public User getCurrentUser() {
		List<User> users = repository.findAll();
		if(users.isEmpty()) return null;
		else return users.get(users.size()-1);
	}
}
