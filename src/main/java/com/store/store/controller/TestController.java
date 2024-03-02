package com.store.store.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // @RequestMapping("/")
    // public String index() {
    // return "index";
    // }

}
