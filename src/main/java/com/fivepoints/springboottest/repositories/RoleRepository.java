package com.fivepoints.springboottest.repositories;

import com.fivepoints.springboottest.entities.ERole;
import com.fivepoints.springboottest.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // find Role by name
    Optional<Role> findByName(ERole name);
}
