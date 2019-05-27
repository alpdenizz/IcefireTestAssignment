package icefire.TestAssignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import icefire.TestAssignment.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
