package icefire.TestAssignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import icefire.TestAssignment.domain.Encrypted;

@Repository
public interface EncryptedRepository extends JpaRepository<Encrypted, String> {
}
