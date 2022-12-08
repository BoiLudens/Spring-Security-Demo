package com.boiludens.demo.controller;

import com.boiludens.demo.model.User;
import com.boiludens.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class AuthController {
    @Autowired
    AuthService authService;

    @PreAuthorize("isAnonymous()")
    @MutationMapping
    public User login(@Argument String email, @Argument String password) {
        return authService.login(email);
    }
}
