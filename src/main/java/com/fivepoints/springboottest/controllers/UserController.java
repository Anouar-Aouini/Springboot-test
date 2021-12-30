package com.fivepoints.springboottest.controllers;

import com.fivepoints.springboottest.entities.User;
import com.fivepoints.spring.payload.responses.MessageResponse;
import com.fivepoints.springboottest.payload.responses.MessageResponseUser;
import com.fivepoints.springboottest.repositories.UserRepository;
import com.fivepoints.springboottest.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;


//    @PostMapping("/")
//    public ResponseEntity<User> saveNewUser(@RequestBody User user)
//    {
//        User savedUser =  this.userService.saveNewUser(user);
//        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
//    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers()
    {
        List<User> listUsers = this.userService.getAllUsers();
        return new ResponseEntity<>(listUsers, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findUserByID(@PathVariable("id") long id)
    {
        User user = this.userService.findUserByID(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseUser> updateUserByID(
            @PathVariable("id") long id,
            @RequestBody User user
            ,@CurrentSecurityContext(expression="authentication") Authentication authentication)
    {
        String connectedEmail = this.userRepository.findByEmail(authentication.getName()).getEmail();
        Optional<User> userData = this.userRepository.findById(id);
        if(userData.get().getEmail() == connectedEmail){
            String message = this.userService.updateUserByID(id, user);
            Optional<User> updateUser = this.userRepository.findById(id);
            return new ResponseEntity<>(new MessageResponseUser(message,updateUser), HttpStatus.OK);
        }else
                return new ResponseEntity<>
                        (new MessageResponseUser
                                ("You are not authorized",userData), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteUserById(@PathVariable("id") long id)
    {
        String message = this.userService.deleteUserById(id);
        return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
    }

    // Affecter Role to user
    @PutMapping("/affect-role/{idUser}/{idRole}")
    public ResponseEntity<MessageResponse> affectUserToRole(long idUser, long idRole) {
        String message = this.userService.affectUserToRole(idUser, idRole);
        return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
    }

}
