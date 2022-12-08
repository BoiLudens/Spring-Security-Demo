package com.boiludens.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)

public class SecurityConfig {
    @Autowired
    JWTFilter jwtFilter;
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {


        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( auth -> { auth.requestMatchers("/graphql", "/graphiql").permitAll(); })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, RequestHeaderAuthenticationFilter.class)
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        UserDetails user = User.withDefaultPasswordEncoder() // (1)
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder() // (2)
                .username("admin")
                .password("password")
                .roles("USER","ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin); // (3)
    }

}
