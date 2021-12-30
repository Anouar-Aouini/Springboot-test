package com.fivepoints.springboottest.services;

import com.fivepoints.springboottest.entities.Role;
import com.fivepoints.springboottest.entities.User;
import com.fivepoints.springboottest.exceptions.ResourceNotFoundException;
import com.fivepoints.springboottest.repositories.RoleRepository;
import com.fivepoints.springboottest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<User> getAllUsers()
    {
        return this.userRepository.findAll();
    }

    public User findUserByID(long id)
    {
        Optional<User> userData = this.userRepository.findById(id);
        // Return statement if user exist or throw exception
        return userData.orElseThrow(() -> new ResourceNotFoundException("User not found"));

    }

    public User getCurrentUser(@CurrentSecurityContext(expression="authentication") Authentication authentication) {

        return this.userRepository.findByEmail(authentication.getName());
    }
    public String makeSureAuthorized (long id,User user,@CurrentSecurityContext(expression="authentication") Authentication authentication) {
        Optional<User> userData = this.userRepository.findById(id);
        this.userRepository.findByEmail(authentication.getName()).getEmail();

        return this.userRepository.findById(id).toString();
    }

    public String updateUserByID(long id, User user)
    {
        Optional<User> userData = this.userRepository.findById(id);
        if (userData.isPresent()) {
            User existingUser = userData.orElseThrow(() -> new ResourceNotFoundException("User not found"));
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            // Change password if exist
            if(!user.getPassword().isEmpty())
            {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            // save existingUser in the database
            this.userRepository.save(existingUser);
            // return statement
            return "User updated successfully!";
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }

    public String deleteUserById(long id)
    {
        Optional<User> userData = this.userRepository.findById(id);
        if (userData.isPresent()) {
            //this.postRepository.deleteById();

            this.userRepository.deleteById(id);
            return "User deleted successfully!";
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }

    // Affecter Role to user
    public String affectUserToRole(long idUser, long idRole) {
        Optional<User> userData = this.userRepository.findById(idUser);
        if (userData.isPresent()) {
            User existingUser = userData.orElseThrow(() -> new ResourceNotFoundException("User not found"));
            Optional<Role> roleData = this.roleRepository.findById(idRole);
            if (roleData.isPresent()) {
                Role existingRole = roleData.orElseThrow(() -> new ResourceNotFoundException("Role not found"));
                Set<Role> roles = existingUser.getRole();
                roles.add(existingRole);
                existingUser.setRole(roles);
                this.userRepository.save(existingUser);
            }
        }
        return "User affected to role successfully!";
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        User user = null;
        try {
            user = userRepository.findByEmail(email);
        } catch (Exception e) {
            throw e;
        }
        return user;
    }


}
