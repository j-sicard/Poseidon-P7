package com.nnk.springboot.controlleurtest;

import com.nnk.springboot.AbstractConfigurationTest;
import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class UserControllerTests extends AbstractConfigurationTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Test
    public void homeTest() {
        User user = new User();
        user.setUsername("UserNameTest");
        user.setPassword("PasswordTest");
        user.setFullname("FullNameTest");
        user.setRole("RoleTest");

        List<User> users = new ArrayList<>();
        users.add(user);

        when(userService.getUsers()).thenReturn(users);
        String viewName = userController.home(model);

        assertEquals("user/list", viewName);
        verify(model).addAttribute("users", users);
    }

    @Test
    public void addUserFormTest() {
        String viewName = userController.addUserForm(model);

        assertEquals("user/add", viewName);

        verify(model).addAttribute(eq("user"), Mockito.any(User.class));
    }

    @Test
    public void validateBidListWithNoErrorsTest() {
        // Objet User valide
        User user = new User();
        user.setRole("USER");
        user.setPassword("unMotDePasseValide");

        // Simuler un BindingResult sans erreurs
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);

        // Appeler la méthode validate
        String viewName = userController.validate(user, result, model);

        verify(userService, times(1)).saveUser(user);

        assertEquals("redirect:/user/list", viewName);
    }


    @Test
    public void validateBidListWithErrorsTest() {
        // Objet BidList invalide
        User user = new User();
        user.setRole("visiteur");

        // Simuler un BindingResult avec des erreurs
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        String viewName = userController.validate(user, result, model);

        verify(userService, never()).deleteUser(user);

        // Vérifier que la vue renvoyée est la vue d'ajout (car il y a des erreurs)
        assertEquals("user/add", viewName);
    }
}
