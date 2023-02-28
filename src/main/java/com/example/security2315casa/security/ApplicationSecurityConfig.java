package com.example.security2315casa.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//deprecated
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class ApplicationSecurityConfig
{
    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService() {
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
