package com.example.security2315casa.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//deprecated
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class ApplicationSecurityConfig
{
    // deprecated
//        extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails joseUser = User.builder()
                .username("jose")
                .password(passwordEncoder.encode("123"))
                .roles(ApplicationUserRol.ADMIN.name())
                .build();
        //esta es una clase que implementa la interface en concreto la que almacena en memoria
        return new InMemoryUserDetailsManager(
                joseUser
        );
    }

}
