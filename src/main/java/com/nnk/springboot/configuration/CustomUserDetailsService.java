package com.nnk.springboot.configuration;

import com.nnk.springboot.model.UserModel;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    // Cette méthode est appelée par Spring Security lorsqu'un utilisateur tente de se connecter
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Rechercher l'utilisateur dans la base de données en fonction du nom d'utilisateur
        UserModel user = userRepository.findByUsername(username);
        System.out.println("Loaded user: " + user.getUsername());
        // Créer un objet UserDetails qui représente l'utilisateur pour Spring Security
        // Cet objet contient le nom d'utilisateur, le mot de passe et les autorisations (rôles) de l'utilisateur
        return new User(user.getUsername(),
                user.getPassword(), getGrantedAuthorities(user.getRole()));
    }


    // Cette méthode convertit les rôles de l'utilisateur en objets GrantedAuthority requis par Spring Security
    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        // Ajouter le rôle de l'utilisateur à la liste d'autorisations
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }

}
