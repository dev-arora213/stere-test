package com.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.store.model.User;
import com.store.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    // @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.getAllUsers();
        System.out.println("users " + users);
        return ResponseEntity.ok(users);
    }

    // // Create a new user
    // @PostMapping
    // public User createUser(@RequestBody User user) {
    // return userService.createUser(user);
    // }

    // // Get all users
    // @GetMapping
    // public List<User> getAllUsers() {
    // return userService.getAllUsers();
    // }

    // // Get user by ID
    // @GetMapping("/{id}")
    // public Optional<User> getUserById(@PathVariable Long id) {
    // return userService.getUserById(id);
    // }

    // // Update user by ID
    // @PutMapping("/{id}")
    // public User updateUser(@PathVariable Long id, @RequestBody User userDetails)
    // {
    // return userService.updateUser(id, userDetails);
    // }

    // // Delete all users
    // @DeleteMapping
    // public String deleteAllUsers() {
    // userService.deleteAllUsers();
    // return "All users have been deleted successfully.";
    // }

    // // Delete user by ID
    // @DeleteMapping("/{id}")
    // public void deleteUser(@PathVariable Long id) {
    // userService.deleteUser(id);
    // }
}