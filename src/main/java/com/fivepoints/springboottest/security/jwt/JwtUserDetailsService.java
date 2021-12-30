package com.fivepoints.springboottest.security.jwt;

import com.fivepoints.springboottest.entities.Role;
import com.fivepoints.springboottest.entities.User;
import com.fivepoints.springboottest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (email.trim().isEmpty()) {
            throw new UsernameNotFoundException("username is empty");
        }

        User user = userService.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User with email = " + email + " not found");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Set<Role> roles = user.getRole();
        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
        });
        return authorities;
    }
}
