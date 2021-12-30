package com.fivepoints.springboottest.controllers;

import com.fivepoints.springboottest.entities.Role;
import com.fivepoints.spring.payload.responses.MessageResponse;
import com.fivepoints.springboottest.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("roles")
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping("/")
    public ResponseEntity<Role> saveNewRole(@RequestBody Role role)
    {
        Role savedRole =  this.roleService.saveNewRole(role);
        return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Role>> getAllRoles()
    {
        List<Role> listRoles = this.roleService.getAllRoles();
        return new ResponseEntity<>(listRoles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findRoleByID(@PathVariable("id") long id)
    {
        Role role = this.roleService.findRoleByID(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateRoleByID(@PathVariable("id") long id, @RequestBody Role role)
    {
        String message = this.roleService.updateRoleByID(id, role);
        return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRoleById(@PathVariable("id") long id)
    {
        String message = this.roleService.deleteRoleById(id);
        return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
    }
}
