package com.store.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.store.store.dto.RegisterUserDto;
import com.store.store.model.Role;
import com.store.store.model.RoleName;
import com.store.store.model.User;
import com.store.store.repository.RoleRepository;
import com.store.store.repository.UserRepository;

import io.micrometer.common.lang.Nullable;
import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    // Create a new user
    @Transactional
    public User createUser(RegisterUserDto registerUser) {
        User inputUser = new User(registerUser.getUsername(), registerUser.getEmail(),
                passwordEncoder.encode(registerUser.getPassword()),
                registerUser.getName());
        System.out.println(inputUser);

        // user service impl
        // add auth
        Set<Role> roles = roleSetFrom(registerUser.getRoles());
        inputUser.setRoles(roles);
        User user = userRepository.save(inputUser);
        System.out.println(user);
        return user;

        // return userRepository.save(user);
    }

    private Set<Role> roleSetFrom(@Nullable Set<String> strRoles) {
        Set<Role> roles = new HashSet<Role>();

        if (strRoles == null) {
            addRole(roles, RoleName.ROLE_USER);
            return roles;
        }

        strRoles.forEach(role -> {
            switch (role.toUpperCase()) {
                case "ADMIN" -> addRole(roles, RoleName.ROLE_ADMIN);
                case "USER" -> addRole(roles, RoleName.ROLE_USER);
                default -> {
                    throw new DataAccessResourceFailureException("Role is not found.");
                }
            }
        });

        return roles;
    }

    private void addRole(Set<Role> roles, RoleName roleName) {
        Role userRole = roleRepository.findByName(roleName)
                .orElseThrow(() -> new DataAccessResourceFailureException("Role is not found."));
        roles.add(userRole);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Update user
    public User updateUser(Long id, User userDetails) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setName(userDetails.getName());
            existingUser.setEmail(userDetails.getEmail());
            return userRepository.save(existingUser);
        }
        return null;
    }

    // Delete all users
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    // Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}