package com.boiludens.demo.config;

import com.boiludens.demo.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.regex.Pattern;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenParam = request.getHeader(AUTHORIZATION_HEADER);
        if (tokenParam != null) {
            tokenParam = tokenParam.replace("Bearer ", "");
            jwtTokenUtil.verifyToken(tokenParam);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    "user",
                    null,
                    AuthorityUtils.createAuthorityList("USER"));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
