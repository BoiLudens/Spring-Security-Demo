package com.boiludens.demo.controller;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class HelloWorldController {

    @PreAuthorize("hasRole('ROLE_USER')")
    @QueryMapping
    public String hello(Principal principal) {
        System.out.println(principal);
        return "potato";
    }

}
