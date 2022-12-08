package com.boiludens.demo.service;

import com.boiludens.demo.model.User;
import com.boiludens.demo.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    public User login(String username) {


        User user = new User();
        user.setName(username);
        user.setToken(jwtTokenUtil.grantToken(username));

        return user;
    }
}
