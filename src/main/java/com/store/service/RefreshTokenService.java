package com.store.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.store.model.RefreshToken;
import com.store.model.User;
import com.store.repository.RefreshTokenRepository;
import com.store.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Transactional
    public RefreshToken createRefreshToken(String username) throws Exception {
        User user = userService.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User  Not Found"));
        RefreshToken prevToken = refreshTokenRepository.findByUserId(user.getId());

        if (prevToken != null) {
            RefreshToken refreshToken = verifyExpiration(prevToken);
            if (refreshToken != null) {
                return refreshToken;
            }
        }

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(60000)) // set expiry of refresh token to 10 minutes - you can
                                                             // configure it application.properties file
                .build();
        if (refreshToken == null) {
            throw new Exception("Null pointer exception refresh token.");
        }
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            // throw new RuntimeException(token.getToken() + " Refresh token is expired.
            // Please make a new login..!");
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(User user) {
        return refreshTokenRepository.deleteByUser(user);
    }

}