package com.nnk.springboot.controlleurtest;

import com.nnk.springboot.AbstractConfigurationTest;
import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.model.UserModel;
import com.nnk.springboot.service.UserService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        UserModel user = new UserModel();
        user.setUsername("UserNameTest");
        user.setPassword("PasswordTest");
        user.setFullname("FullNameTest");
        user.setRole("RoleTest");

        List<UserModel> users = new ArrayList<>();
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

        verify(model).addAttribute(eq("user"), Mockito.any(UserModel.class));
    }

    @Test
    public void validateBidListWithNoErrorsTest() {
        // Objet User valide
        UserModel user = new UserModel();
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
        UserModel user = new UserModel();
        user.setRole("visiteur");

        // Simuler un BindingResult avec des erreurs
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        String viewName = userController.validate(user, result, model);

        verify(userService, never()).deleteUser(user);

        // Vérifier que la vue renvoyée est la vue d'ajout (car il y a des erreurs)
        assertEquals("user/add", viewName);
    }

    @Test
    public void testShowUpdateForm() {
        Integer userId = 1;
        UserModel expectedUser = new UserModel();
        expectedUser.setId(userId);

        // retourne l'enchère simulée lorsque getById est appelé
        when(userService.getById(userId)).thenReturn(Optional.of(expectedUser));

        // Appelez la méthode showUpdateForm
        String viewName = userController.showUpdateForm(userId, model);

        // Vérifie que l'objet User a été ajouté au modèle avec le nom "user"
        verify(model).addAttribute("user", expectedUser);

        // Vérifie que le nom de la vue retournée est "user/update"
        assertEquals("user/update", viewName);
    }

    @Test
    public void deleteUserTest() {
        Integer userId = 1;
        UserModel user = new UserModel();
        user.setId(userId);

        // Configure le service pour retourner l'enchère lorsqu'on vérifie la présence de l'enchère
        when(userService.getById(userId)).thenReturn(Optional.of(user));

        // Appele la méthode deleteUser
        String viewName = userController.deleteUser(userId, model);

        // Vérifie que l'enchère est supprimée avec le service
        verify(userService).deleteUser(user);

        // Vérifie que le nom de la vue retournée est "redirect:/user/list"
        assertEquals("redirect:/user/list", viewName);

        // Vérifie que la liste des enchères est ajoutée au modèle
        verify(model).addAttribute(eq("users"), anyList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteUserInvalidId() {
        Integer userId = 999;

        // Configure le service pour retourner false lorsqu'on vérifie la présence de l'enchère
        when(userService.getById(userId)).thenReturn(Optional.empty());

        // Appele la méthode deleteBid avec l'ID factice
        userController.deleteUser(userId, model);
    }

}
