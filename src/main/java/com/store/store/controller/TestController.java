package com.store.store.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/test")
public class TestController {

    // @GetMapping("/")
    // public Map<String, Object> user(@AuthenticationPrincipal OAuth2User
    // principal) {
    // System.out.println("user called");
    // return Collections.singletonMap("name", principal.getAttribute("name"));
    // }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String res() {
        System.out.println("user called");
        return "res";
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/admin")
    public String adminRole() {
        System.out.println("admin route called..");
        return "admin role";
    }

    @GetMapping("/user")
    public String userRole() {
        System.out.println("non admin user route called");
        return "non admin user route called";
    }

}
