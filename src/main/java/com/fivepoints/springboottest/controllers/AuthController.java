package com.fivepoints.springboottest.controllers;

import com.fivepoints.springboottest.exceptions.EmailAlreadyUsedException;
import com.fivepoints.springboottest.payload.requests.LoginRequest;
import com.fivepoints.springboottest.payload.requests.RegisterRequest;
import com.fivepoints.spring.payload.responses.LoginResponse;
import com.fivepoints.spring.payload.responses.MessageResponse;
import com.fivepoints.springboottest.repositories.UserRepository;
import com.fivepoints.springboottest.services.AuthService;
import com.fivepoints.springboottest.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest)
    {
        String token = this.authService.login(loginRequest);
        return ResponseEntity.ok(new LoginResponse(token,"Bearer", "Login successfully",loginRequest.getEmail()));
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterRequest registerRequest) throws EmailAlreadyUsedException {
        String message = this.authService.register(registerRequest);
        return ResponseEntity.ok(new MessageResponse(message));
    }

    //getting the email of the connected user
    @GetMapping("/currentuser")
    public ResponseEntity<MessageResponse>
    getCurrentUser(@CurrentSecurityContext(expression="authentication") Authentication authentication) {

        if (authentication != null)
            return ResponseEntity.ok(new MessageResponse(authentication.getName()));
        else
            return ResponseEntity.ok(new MessageResponse(""));
    }

    //getting the whole object of the connected user
    @GetMapping("/activeuser")
    public User getActiveUser(@CurrentSecurityContext(expression="authentication") Authentication authentication){

        return this.userRepository.findByEmail(authentication.getName());
    }

    //return a true if the provided id is the connected user's id
    @GetMapping("/active/{id}")
    public  Boolean getActive(@PathVariable("id")  long id
            ,@CurrentSecurityContext(expression="authentication") Authentication authentication){

        String connectedEmail = this.userRepository.findByEmail(authentication.getName()).getEmail();
        Optional<User> userData = this.userRepository.findById(id);

        return userData.get().getEmail() == connectedEmail;
    }
}
