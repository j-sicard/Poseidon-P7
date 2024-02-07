package com.nnk.springboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig  {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // Configuration de la chaîne de filtres de sécurité pour les requêtes HTTP
   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
       // Configurer les autorisations pour les différentes URL de l'application
        http
                .authorizeRequests((requests)-> requests
                // Autoriser l'accès aux URL spécifiées pour les utilisateurs ayant les rôles USER ou ADMIN
                .requestMatchers("/user/**", "/bidList/**", "/curvePoint/**", "/rating/**", "/ruleName/**", "/trade/**").hasAnyRole("USER", "ADMIN")
                // Toutes les autres requêtes doivent être authentifiées
                .anyRequest().authenticated()
        )
                // Configurer le formulaire de connexion
                .formLogin((form) -> form
                        .permitAll()// Permettre à tous les utilisateurs d'accéder au formulaire de connexion
                )
                // Configurer la déconnexion
                .logout((logout) -> logout.permitAll());// Permettre à tous les utilisateurs de se déconnecter
       // Construire et retourner la chaîne de filtres de sécurité configurée
       return http.build();
    }

    /**
     * Configure et retourne un gestionnaire d'authentification.
     * <p>
     * Cette méthode configure le gestionnaire d'authentification à utiliser avec
     * l'encodeur de mot de passe spécifié et le système de sécurité HTTP.
     *
     * @param http Le contexte de sécurité HTTP à configurer.
     * @param bCryptPasswordEncoder L'encodeur de mot de passe BCrypt à utiliser.
     * @return Un objet AuthenticationManager configuré.
     * @throws Exception Si une erreur se produit lors de la configuration du gestionnaire.
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }

    // Configuration d'un bean pour utiliser BCryptPasswordEncoder
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        // Retourne une instance de BCryptPasswordEncoder pour encoder les mots de passe
        return new BCryptPasswordEncoder();
    }

}

