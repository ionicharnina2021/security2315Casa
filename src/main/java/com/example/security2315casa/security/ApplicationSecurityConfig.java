package com.example.security2315casa.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//deprecated
import org.springframework.http.HttpMethod;
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
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/","/index.html","/css/*","/assets/*","/js/*")
                .permitAll()
                .requestMatchers(HttpMethod.POST,"/students/add")
                .hasAuthority(ApplicationUserPermission.STUDENT_WRITE.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails joseUser = User.builder()
                .username("jose")
                .password(passwordEncoder.encode("123"))
                .authorities(ApplicationUserPermission.STUDENT_WRITE.getPermission())
                .build();
        UserDetails luisUser = User.builder()
                .username("luis")
                .password(passwordEncoder.encode("123"))
                .roles(ApplicationUserRol.GUEST.name())
                .build();
        //esta es una clase que implementa la interface en concreto la que almacena en memoria
        return new InMemoryUserDetailsManager(
                joseUser,luisUser
        );
    }


}
