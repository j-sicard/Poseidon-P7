package com.nnk.springboot.configurationtest;

import com.nnk.springboot.AbstractConfigurationTest;
import com.nnk.springboot.configuration.CustomUserDetailsService;
import com.nnk.springboot.model.UserModel;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomUserDetailsServiceTests extends AbstractConfigurationTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    @Test
    public void testLoadUserByUsername() {
        UserModel mockUser = new UserModel();
        mockUser.setUsername("testUser");
        mockUser.setPassword("testPassword");
        mockUser.setRole("ROLE_USER");

        when(userRepository.findByUsername("testUser")).thenReturn(mockUser);
        // Appele la méthode loadUserByUsername
        UserDetails userDetails = userDetailsService.loadUserByUsername("testUser");

        assertEquals("testUser", userDetails.getUsername());
        assertEquals("testPassword", userDetails.getPassword());
        assertEquals("ROLE_ROLE_USER", userDetails.getAuthorities().iterator().next().getAuthority());
    }
}

