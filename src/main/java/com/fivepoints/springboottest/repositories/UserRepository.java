package com.fivepoints.springboottest.repositories;

import com.fivepoints.springboottest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // return True if email already exist
    boolean existsByEmail(String email);
    // find user by email address
    User findByEmail(String email);
}
