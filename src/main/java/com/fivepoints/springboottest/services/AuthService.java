package com.fivepoints.springboottest.services;

import com.fivepoints.springboottest.entities.ERole;
import com.fivepoints.springboottest.entities.Role;
import com.fivepoints.springboottest.entities.User;
import com.fivepoints.springboottest.exceptions.EmailAlreadyUsedException;
import com.fivepoints.springboottest.exceptions.ResourceNotFoundException;
import com.fivepoints.springboottest.payload.requests.LoginRequest;
import com.fivepoints.springboottest.payload.requests.RegisterRequest;
import com.fivepoints.springboottest.repositories.RoleRepository;
import com.fivepoints.springboottest.repositories.UserRepository;
import com.fivepoints.springboottest.security.jwt.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    // pour crypter le password (NB: il faut ajouter le bean BCryptPasswordEncoder dans l'application)
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    public String login(LoginRequest loginRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return this.jwtTokenUtils.generateToken(userDetails);
    }

    public String register(RegisterRequest registerRequest) throws EmailAlreadyUsedException {
        // test if email already used
        if (this.userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EmailAlreadyUsedException("Error: Email is already in use!");
        }
        // Create new user account
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        // Traitement des Roles
        Set<String> registerRequestRoles = registerRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        // find Roles
        if (registerRequestRoles == null) {
            Role guestRole = this.roleRepository.findByName(ERole.CLIENT)
                    .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
            roles.add(guestRole);
        }

        // Affect User Roles
        user.setRole(roles);
        this.userRepository.save(user);
        return "User registered successfully!";
    }
}
