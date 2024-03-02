package com.store.store.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.store.store.dto.LoginUserDto;
import com.store.store.dto.RegisterUserDto;
import com.store.store.model.User;
import com.store.store.repository.UserRepository;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User signup(RegisterUserDto input) throws Exception {
        try {
            User inputUser = new User(input.getUsername(), input.getEmail(),
                    passwordEncoder.encode(input.getPassword()),
                    input.getName());
            System.out.println(inputUser);

            User user = userRepository.save(inputUser);
            System.out.println(user);
            return user;
        } catch (Exception exception) {
            throw new Exception(exception);
        }
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }

}
