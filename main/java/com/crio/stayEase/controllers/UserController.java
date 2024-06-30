package com.org.StayEase.controllers;

import com.org.StayEase.entities.User;
import com.org.StayEase.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired

    public UserService userService;

    // Endpoint to get all users, accessible by ADMIN and MANAGER roles

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    // Endpoint to get a user by email, accessible by ADMIN and MANAGER roles

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/emailId")
    public ResponseEntity<User> getUserByEmail(@RequestBody String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    // Endpoint to get a user by ID, accessible by ADMIN and MANAGER roles

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    // Endpoint to update a user, accessible by ADMIN, MANAGER, and CUSTOMER
    // roles

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','CUSTOMER')")
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    // Endpoint to delete a user by ID, accessible by ADMIN and MANAGER roles
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        // System.out.println("\nEmail : "+userId);
        userService.deleteUser(userId);
        return new ResponseEntity<>("The user has been removed successfully.", HttpStatus.OK);
    }

}