package com.store.store.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.store.store.dto.LoginUserDto;
import com.store.store.dto.RegisterUserDto;
import com.store.store.model.User;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthenticationService(UserService userService,
            AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    public User signup(RegisterUserDto input) throws Exception {
        try {
            return userService.createUser(input);
        } catch (Exception exception) {
            throw new Exception(exception);
        }
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
        return userService.findByEmail(input.getEmail()).orElseThrow();
    }

}
