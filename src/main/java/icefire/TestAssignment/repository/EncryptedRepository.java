package icefire.TestAssignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import icefire.TestAssignment.domain.Encrypted;

/**
 * 
 * @author denizalp@ut.ee
 * <p>Jpa Repository for Encrypted</p>
 */
@Repository
public interface EncryptedRepository extends JpaRepository<Encrypted, String> {
}
