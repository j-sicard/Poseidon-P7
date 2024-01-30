package com.nnk.springboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig  {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    /*Syntaxe fonctionnelle*/
   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeRequests((requests)-> requests
                .requestMatchers("/user/**", "/bidList/**", "/curvePoint/**", "/rating/**", "/ruleName/**", "/trade/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
        )
                .formLogin((form) -> form
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());
        return http.build();
    }
    /*Configuration déclarative*/
 /*   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/bidList/**", "/curvePoint/**", "/rating/**", "/ruleName/**", "/trade/**").hasRole("ADMIN");
            auth.requestMatchers("/bidList/**", "/curvePoint/**", "/rating/**", "/ruleName/**", "/trade/**").hasRole("USER");
            auth.anyRequest().authenticated();
        }).formLogin(Customizer.withDefaults()).build();
    }*/
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

