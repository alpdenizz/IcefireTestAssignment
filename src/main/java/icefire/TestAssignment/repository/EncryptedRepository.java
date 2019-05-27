package icefire.TestAssignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import icefire.TestAssignment.domain.Encrypted;
import icefire.TestAssignment.domain.User;

@Repository
public interface EncryptedRepository extends JpaRepository<Encrypted, String> {

	public List<Encrypted> findByUser(User user);
}
